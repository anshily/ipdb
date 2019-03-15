package io.github.anshily.ipdb.core.util;

public class Data {
    /**
     * 左孩子
     */
    public Link lc;

    /**
     * 右孩子
     */
    public Link rc;

    public int data;

    /**
     * 是否为空
     */
    public boolean empty;

    public Data(){
        this.data = 0;
        this.empty = true;
        this.rc = new Link();
        this.lc = new Link();
    }

    public Data(int data){
        this.data = data;
        this.empty = false;
        this.rc = new Link();
        this.lc = new Link();
    }

    /**
     * 比较当前值和右值的大小
     * 若大于右值返回 1 小于右值返回 -1
     * @return
     */
    public int compare(Data rd){
        int ret = 1;
        if (rd.data > this.data){
            ret = -1;
        }else if (rd.data == this.data){
            ret = 0;
        }
        return ret;
    }

    /**
     * 是否为空
     */
    public boolean isEmpty(){
        return this.empty;
    }

    public void setEmpty(boolean b){
        this.empty = b;
    }
}
