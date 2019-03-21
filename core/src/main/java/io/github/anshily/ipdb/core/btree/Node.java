package io.github.anshily.ipdb.core.btree;


import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {
    int n;
    int nodeId;
    int parentId;
    boolean leaf;
    ArrayList<Data> key;
    ArrayList<Integer> child;

    Node(){
        this(0);
    }
    Node(int nodeId){
        this(nodeId,0);
    }
    Node(int nodeId, int n){
        this.parentId = 0;
        this.nodeId = nodeId;
        this.leaf = true;
        this.n = n;
        this.key = new ArrayList<Data>(10);
        this.child = new ArrayList<Integer>(10);
    }

    Node(int nodeId, int n,int parent){
        this.parentId = parent;
        this.nodeId = nodeId;
        this.leaf = true;
        this.n = n;
        this.key = new ArrayList<Data>(10);
        this.child = new ArrayList<Integer>(10);
    }


    public void updataNodeKey(int i,Data data){
        if (this.key.size() > i){
            this.key.set(i, data);
        }else {
            this.key.add(i, data);
        }
    }

    public void updataNodeChild(int i,Integer c){
        if (this.child.size() > i){
            this.child.set(i, c);
        }else {
            this.child.add(i, c);
        }
    }
}
