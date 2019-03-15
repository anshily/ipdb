package io.github.anshily.ipdb.core.tree;

public class T1 {


    public static void main(String[] args){

        BtreeModify btreeModify = new BtreeModify();
        BtreeModify.KeyNode[] keyNodes = {new BtreeModify.KeyNode(14,"abc"),
                new BtreeModify.KeyNode(23,"bcd"),
                new BtreeModify.KeyNode(2,"cde"),
                new BtreeModify.KeyNode(16,"def"),
                new BtreeModify.KeyNode(21,"efg"),
                new BtreeModify.KeyNode(15,"fgh")};
        btreeModify.createBTree(keyNodes);
        btreeModify.add(new BtreeModify.KeyNode(17,"ghi"));
        btreeModify.add(new BtreeModify.KeyNode(9,"hij"));
        btreeModify.add(new BtreeModify.KeyNode(7,"ijk"));
    }
}
