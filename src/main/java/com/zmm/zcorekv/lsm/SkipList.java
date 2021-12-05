package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.utils.Const;
import com.zmm.zcorekv.utils.Entry;
import com.zmm.zcorekv.utils.Rand;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zmm
 * @date 2021/11/28 19:45
 */
public class SkipList extends MemTable {

    private Node header = new Node();

    /** SkipList最大高度 */
    private int maxLevel = Const.MAX_SKIP_LIST_LEVEL;

    /** 当前SkipList层高 */
    private int level = 0;
    private int length = 0;
    private final Lock lock = new ReentrantLock();
    private long size;

    @Override
    public void init(LSMOptions options) {
        // 初始化跳表
        maxLevel = Math.min(options.maxLevel, Const.MAX_SKIP_LIST_LEVEL);

        // 恢复逻辑
        recovery();
    }

    @Override
    public boolean set(Entry entry) {
        lock.lock();
        try {
            float score = calcScore(entry.key);
            // 前继节点
            Node[] preNode = new Node[level];
            // 找插入的位置
            Node p = header;
            // 竖着找
            // 遍历节点p的levels
            List<Node> levels = p.levels;
            Node next;
            for (int i = level - 1; i >= 0; i--) {

                // 获取该层的next节点
                next = levels.get(i);
                // 横向找，直到下一个分数大于等于当前分数或者下一个为null
                while (next != null) {

                    // 与当前key进行比较
                    int flag = compare(score, entry.key, next);
                    if (flag == -1) {
                        // 下一层
                        break;
                    } else if (flag == 0) {
                        // 相同，直接修改并返回
                        next.entry = entry;
                        return true;
                    }

                    // 下一个节点
                    p = next;
                    levels = p.levels;
                    next = levels.get(i);
                }

                // 添加前继节点
                preNode[i] = p;
            }
            // 构建新节点
            Node newNode = new Node(entry, score);
            // 获取新节点层数
            int cLevel = randLevel();
            // 添加节点
            for (int i = 0; i < Math.min(cLevel, level); i++) {
                Node swap = preNode[i].levels.get(i);
                preNode[i].levels.set(i, newNode);
                newNode.addLevel(swap);
            }
            // 判断是否需要增加层数
            while (cLevel > level) {
                // 将头节点层数增加
                header.levels.add(newNode);
                newNode.addLevel(null);
                level++;
            }
            length++;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public byte[] get(byte[] key) {
        lock.lock();
        try {
            float score = calcScore(key);
            List<Node> levels = header.levels;
            for (int i = level - 1; i >= 0; i--) {
                Node next = levels.get(i);
                while (next != null) {
                    int flag = compare(score, key, next);
                    if (flag == 0) {
                        return next.getEntry().value;
                    } else if (flag == -1){
                        break;
                    }
                    levels = next.levels;
                    next = levels.get(i);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean del(byte[] key) {
        return set(new Entry(key, null));
    }

    @Override
    public void close() {

    }

    @Override
    public void recovery() {

    }

    /**
     * 随机生成节点层数（Redis实现）
     * @return          node's level
     */
    private int randLevel() {
        // 一亿次调用，平均耗时1.391s
        int level = 1;
        // 随机生成一个值，
        float expect = maxLevel * Const.DEFAULT_SKIP_LIST_P;
        while (Rand.rand(maxLevel) < expect && level < maxLevel) {
            level++;
        }
        return level;

        //// corekv实现
        //// 一亿次调用，平均耗时2s
        //int level = 1;
        //for (; level < 64; level++) {
        //    if (Rand.rand() == 0) {
        //        return level;
        //    }
        //}
        //return level;

        //// Redis实现
        //// 一亿次调用，1.405s
        //int level = 1;
        //float expect = Const.DEFAULT_SKIP_LIST_P * 0xFFFF;
        //// Redis中是取的低16位进行比较，这里就直接在0~65535中间生成了
        //while (Rand.rand(65535) < expect && level < 64)
        //    level++;
        //return level;
    }

    /**
     * 算法参考自corekv
     * 生成key的分值（因为最终要转换为sst，需要对Key排序）
     * @param key       key
     * @return          分值
     */
    private static float calcScore(byte[] key) {
        int l = Math.min(key.length, 8);
        long hash = 0;
        for (int i = 0 ; i < l; i++) {
            int j = 64 - 8 - i * 8;
            hash |= key[i] << j;
        }
        return hash;
    }

    /**
     * 比较entry A和B的大小
     * @param score     A的分值
     * @param key       A的key
     * @param node      B
     * @return          1: 大于  0: 等于  -1: 小于
     */
    private int compare(float score, byte[] key, Node node) {
        if (score == node.score) {
            byte[] nodeKey = node.entry.key;
            for (int i = 0; i < key.length && i < nodeKey.length; i++) {
                if (key[i] > nodeKey[i]) {
                    return 1;
                } else if (key[i] < nodeKey[i]) {
                    return -1;
                }
            }
            if (key.length == nodeKey.length) {
                return 0;
            } else if (key.length > nodeKey.length) {
                return 1;
            }
            return -1;
        }
        return score > node.score ? 1 : -1;
    }

    class Node {
        private List<Node> levels = new ArrayList<>();
        private Entry entry;
        private float score;

        public Node() {}

        public Node(Entry entry, float score) {
            this.entry = entry;
            this.score = score;
        }

        public void addLevel(Node node) {
            levels.add(node);
        }

        public List<Node> getLevels() {
            return levels;
        }

        public void setLevels(List<Node> levels) {
            this.levels = levels;
        }

        public Entry getEntry() {
            return entry;
        }

        public void setEntry(Entry entry) {
            this.entry = entry;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }
    }

    public void printSkipList() {
        List<Node> nodes = new ArrayList<>();
        Node node = header;
        while (node != null) {
            nodes.add(node);
            node = node.levels.get(0);
        }
        for (int i = level - 1; i >= 0; i--) {
            for (int j = 0; j < length + 1; j++) {
                node = nodes.get(j);
                if (node.levels.size() <= i) {
                    System.out.print("           ");
                } else if (node.entry == null){
                    System.out.print("k: " + new String(node.levels.get(i).entry.key) + ", v: " + new String(node.levels.get(i).entry.value) + " ");
                } else {
                    System.out.print("k: " + new String(node.entry.key) + ", v: " + new String(node.entry.value) + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------------------");
    }
}
