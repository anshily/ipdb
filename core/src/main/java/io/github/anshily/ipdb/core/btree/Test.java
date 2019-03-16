package io.github.anshily.ipdb.core.btree;

import io.github.anshily.ipdb.core.util.StoreHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

    public static void main(String[] args){
        Tree tree = new Tree();
//        tree.insertData(new Data(10,"10test"));
//        tree.insertData(new Data(21,"21test"));
//        tree.insertData(new Data(13,"13test"));
//        tree.insertData(new Data(17,"17test"));
//        tree.insertData(new Data(21,"22test"));
//        tree.insertData(new Data(7,"7test"));
//        tree.insertData(new Data(9,"9test"));
//        tree.insertData(new Data(8,"8test"));
//        tree.insertData(new Data(6,"6test"));
        for (int i = 1; i < 1000; i++) {
            tree.insertData(new Data(i,"test"+i));
        }
//        try {
//            StoreHelper storeHelper = new StoreHelper(tree.storeBase);
//            storeHelper.store();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        tree.insertData(new Data(5,"5test"));

        tree.count();
    }
}
