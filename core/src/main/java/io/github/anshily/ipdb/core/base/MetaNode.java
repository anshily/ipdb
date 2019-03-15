package io.github.anshily.ipdb.core.base;


public class MetaNode {

    public int DEG = 10;

    public Node[] metaArr;

    public MetaNode(){
        metaArr = new Node[DEG];
    }


    public int search(int index){

        for (int i = 0; i < DEG; i++) {
            if (metaArr[i].index == index){
                return i;
            }
        }
        return -1;
    }

    public class Node {
        public int index;
        public String link;
    }

}
