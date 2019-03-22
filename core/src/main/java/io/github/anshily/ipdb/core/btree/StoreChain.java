package io.github.anshily.ipdb.core.btree;

import java.io.*;
import java.util.ArrayList;

public class StoreChain {
    public static void stroe(Object object){
        ObjectOutputStream oo = null;
        File file = new File("store.obj");
        try {

            oo = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oo.writeObject(object);
            System.out.println("文件长度");
            System.out.println(file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Person对象序列化成功！");
        try {
            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Node> cat(){
        ArrayList<Node> nodes = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("store.obj")));
            try {
                nodes = (ArrayList<Node>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodes;
    }
}
