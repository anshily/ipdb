package io.github.anshily.ipdb.core.btree;


import java.util.ArrayList;

public class Node {
    int n;
    ArrayList<Integer> key;
    boolean leaf;
    ArrayList<Integer> c;
    int nodeId;

    Node(){
        this(0);
    }
    Node(int nodeId){
        this(nodeId,0);
    }
    Node(int nodeId, int n){
        this.nodeId = nodeId;
        this.leaf = true;
        this.key = new ArrayList<Integer>(10);
        this.c = new ArrayList<Integer>(10);
        this.n = n;
    }

}
