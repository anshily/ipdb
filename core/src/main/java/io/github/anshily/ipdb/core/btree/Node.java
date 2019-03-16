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
        this.parentId =
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
}
