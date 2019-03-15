package io.github.anshily.ipdb.core.btree;

import java.util.ArrayList;

import static io.github.anshily.ipdb.core.base.Constants.MINMUM_DEGREE;

public class Tree {

    ArrayList<Node> storeBase;
    int rid;
    int tail;
    int t;

    Tree(){
        this.t = MINMUM_DEGREE;
        this.tail = 0;
        this.rid = 0;
        this.storeBase = new ArrayList<Node>();
        Node node = new Node();
        this.storeBase.add(node.nodeId, node);
    }

    /**
     * 每个叶结点包含的关键字个数有上界和下界。用一个被称为B树的最小度数（minmum degree）的固定整数 t >= 2 来表示这个界：
     * a. 除根节点以外的每个内部节点至少有 t 个孩子，除根节点以外的每个结点至少有 t-1 个关键字。如果树非空，根结点至少有一个关键字。
     * b. 每个内部结点最多有 2t 个孩子， 最多有 2t-1 个关键字。如果一个节点恰好有 2t-1 个关键字，则称该结点是满的（full）。
     * ---------------------
     * 作者：F小二V
     * 来源：CSDN
     * 原文：https://blog.csdn.net/u014165620/article/details/82976882
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     */

    // 0  0  1  1      i-1 i-1   n-2  n-2  n-1
    // k1 c1 k2 c2 ... ki ci ... kn-1 cn-1 kn
    //分裂x结点的第i个孩子
    public void splitChild(Node x, int i) {
        x = storeBase.get(x.nodeId);
        /**
         * 第i个孩子 索引为 i-1
         */
        Node y = storeBase.get(x.c.get(i));
        Node z = allocateNode();
        z.leaf = y.leaf;
        z.n = t - 1;
        //使用y后半部分的关键字初始化z的关键字
        /**
         * 总 2t-1 个 key 当前节点拥有 t-1个 key 当 j=t-2 时 获取第 2t-2 个 key
         */

        for (int j = 0; j < t-1; j++) {
            if (z.key.size() > j){
                z.key.set(j,y.key.get(j+t));
            }else {
                z.key.add(j,y.key.get(j+t));
            }
            y.key.remove(j+t);
        }
        y.n = t - 1;

        /**
         * 将x中i后面的所有指向孩子的指针向后移一位
         * 指针 2t 个 将索引从 i 到 2t-1 的指针后移
         */
        for (int j = x.n-1; i < j; j--) {
            if (x.c.size() > j+1){
                x.c.set(j+1,x.c.get(j));
            }else {
                x.c.add(j+1,x.c.get(j));
            }

        }
        //x的第 i 个孩子为原节点 第（i+1）个孩子为新结点z
        if (x.c.size() > i+1){
            x.c.set(i+1, z.nodeId);
        }else {
            x.c.add(i+1, z.nodeId);
        }

        //将x中i后面的所有关键字向后移一位

        for (int j = x.n-1; i <= j; j--) {
            if (x.key.size() > j+1){
                x.key.set(j+1,x.key.get(j));
            }else {
                x.key.add(j+1,x.key.get(j));
            }
        }

        //将y的中间关键字y.key[t]向上提为父结点x的第i个关键字
        if (x.key.size() > i){
            x.key.set(i,y.key.get(t-1));
        }else {
            x.key.add(i,y.key.get(t-1));
        }
        y.key.remove(t-1);
        x.n = x.n + 1;
        if (storeBase.size() > x.nodeId){
            storeBase.set(x.nodeId, x);
        }else {
            storeBase.add(x.nodeId, x);
        }
        if (storeBase.size() > y.nodeId){
            storeBase.set(y.nodeId, y);
        }else {
            storeBase.add(y.nodeId, y);
        }
        if (storeBase.size() > z.nodeId){
            storeBase.set(z.nodeId, z);
        }else {
            storeBase.add(z.nodeId, z);
        }
    }

    //在B树T中插入关键字k
    public void insertData(int k) {
        Node r = storeBase.get(rid);
        //如果根结点r是满的，需要向上新提一个根结点
        if (r.n == 2*t - 1) {
            Node s = allocateNode();
            rid = s.nodeId;
            s.leaf = false;
            s.n = 0;

            if (s.c.size() > 0){
                s.c.set(0, r.nodeId);
            }else {
                s.c.add(0, r.nodeId);
            }
            updataBaseStroe(s);
            splitChild(s, 0);

            //向以非满结点s为根的树中插入关键字k
            insertNonFull(s, k);
        } else {
            insertNonFull(r, k);
        }
    }

    //向以非满结点x为根的树中插入关键字k
    public void insertNonFull(Node x, int k) {
        x = storeBase.get(x.nodeId);
        int i = x.n;
        //叶结点，直接在该结点插入
        if (x.leaf) {

            boolean eq = false;

            while (i >= 1 && k <= x.key.get(i-1)) {

                if (k != x.key.get(i-1)){
                    if (x.key.size() > i){
                        x.key.set(i, x.key.get(i-1));
                    }else {
                        x.key.add(i, x.key.get(i-1));
                    }
                }else {
                    eq = true;
                }
//                x.key.set(i, x.key.get(i-1));
                i = i - 1;
            }
            if (x.key.size() > i){
                x.key.set(i, k);
            }else {
                x.key.add(i, k);
            }
            if (!eq){
                x.n = x.n + 1;
            }
            if (storeBase.size() > x.nodeId){
                storeBase.set(x.nodeId, x);
            }else {
                storeBase.add(x.nodeId, x);
            }
        }
        //内部结点，需要找到插入的叶结点位置
        else {
            while (i >= 1 && k < x.key.get(i-1)) {
                i = i - 1;
            }
//            storeBase.get(x.c.get(i));
            Node node = storeBase.get(x.c.get(i));
            if (isFull(node)) {
                splitChild(x, i);
                if (k > x.key.get(i)) {
                    i = i + 1;
                }
            }
            insertNonFull(storeBase.get(x.c.get(i)), k);
        }
    }

    public Node allocateNode() {
        return new Node(++this.tail);
    }

    public boolean isFull(Node node){
        if (2*t-1 == node.key.size()){
            return true;
        }else {
            return false;
        }
    }

    public void updataBaseStroe(Node node){
        if (node.key.size() > node.nodeId){
            this.storeBase.set(node.nodeId, node);
        }else {
            this.storeBase.add(node.nodeId, node);
        }
    }
}
