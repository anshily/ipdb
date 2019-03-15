package io.github.anshily.ipdb.core.util;

public class T1 {
    int root;
    Node[] storeBase;
    T1(){
        storeBase = new Node[100];
        for (int i = 0; i < storeBase.length; i++) {
            storeBase[i] = new Node();
        }
    }

    //分裂x结点的第i个孩子
    public void splitChild(Node x, int i){
        Node y = storeBase[x.data[i].lc.getPoint()];
        //分配新节点z
        Node z = allocateNode();
        //使用y后半部分的关键字初始化z的关键字
        for (int j = y.data.length/2,k = 0; j < y.data.length; j++, k++) {
            z.data[k] = y.data[j];
        }

        //将x中i后面的所有指向孩子的指针向后移一位
        for (int j = x.data.length-1; j > i; j--) {
            x.data[j] = x.data[j-1];
        }
        //x的第i个孩子为新结点z
//        x.data[x.data[i].lc.getPoint()] =
    }

    //在B树T中插入关键字k
    public void insertData(Data k){

    }

    //向以非满结点x为根的树中插入关键字k
    public void insertNonFull(Node x,Data k){

    }

    public Node allocateNode(){
        return new Node();
    }
}
