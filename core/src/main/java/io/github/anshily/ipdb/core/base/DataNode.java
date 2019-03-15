package io.github.anshily.ipdb.core.base;


import java.io.Serializable;

public class DataNode implements Serializable {

    public int DEG = 10;

    public Node[] dataArr;

    public int aLong = 1234;

    public DataNode(){
        dataArr = new Node[DEG];
        for (int i = 0; i < DEG; i++) {
            dataArr[i] = new Node(i,"安石:"+i);
        }
    }

    public class Node {
        private int index;
        private String data;
        private Node(){
            this.index = 1;
            this.data = "data-1";
        }
        private Node(int idx, String data) {
            this.index = idx;
            this.data = data;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public int search(int index){

        for (int i = 0; i < DEG; i++) {
            if (dataArr[i].index == index){
                return i;
            }
        }
        return -1;
    }

    public int getDEG() {
        return DEG;
    }

    public void setDEG(int DEG) {
        this.DEG = DEG;
    }

    public Node[] getDataArr() {
        return dataArr;
    }

    public void setDataArr(Node[] dataArr) {
        this.dataArr = dataArr;
    }
}
