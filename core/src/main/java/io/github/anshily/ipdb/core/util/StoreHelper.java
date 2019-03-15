package io.github.anshily.ipdb.core.util;

import com.alibaba.fastjson.JSON;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class StoreHelper {
    private long cur = 1;
    private Object object;
    private String string;
    private RandomAccessFile file;

    public StoreHelper(Object obj) throws FileNotFoundException {
        this.object = obj;
        this.string = JSON.toJSONString(obj);
        this.file = new RandomAccessFile("test.dat", "rw");
//        StoreHelper(obj,"test.dat");
    }

    public StoreHelper(Object obj,String filename) throws FileNotFoundException {
        this.object = obj;
        this.string = JSON.toJSONString(obj);
        this.file = new RandomAccessFile(filename, "rw");
    }

    public void reset(Object obj){
        this.object = obj;
        this.string = JSON.toJSONString(obj);
    }

    public void store() throws IOException {
        this.file.write(this.string.getBytes());
    }

    public String cat() throws IOException {
        this.file.seek(0L);
        byte[] str = new byte[this.string.getBytes().length];
        this.file.read(str);
        return new String(str, StandardCharsets.UTF_8);
    }

    public void insert(long data) throws IOException {

        // 第一个节点
        if (this.file.length() == 0)
        {
//            this.file.writeLong(data);
//            this.file.writeLong(48L);
            this.file.writeLong(0L);

            this.file.writeLong(data);
            this.file.writeLong(0L);

            this.file.writeLong(2L);
            this.file.writeLong(0L);

//            this.cur = 6*8;
        }else {
            if (this.cur == 1){
                this.file.seek(0L);
                this.file.skipBytes(8);
                // data 小于 d1 将 d1 后移
                long curData = this.file.readLong();
                System.out.println("当前值"+curData);
                System.out.println("插值"+data);
                if (data < curData){
                    this.file.skipBytes(8);
                    this.file.writeLong(data);
                    this.file.skipBytes(8);
                    this.file.writeLong(curData);
                    this.cur++;
                }
            }else { // 分裂出两个子节点

                this.file.seek(0L);
                this.file.writeLong(40L);
                this.file.seek(40L);
                this.file.writeLong(0L);

                this.file.writeLong(data);
                this.file.writeLong(0L);

                this.file.writeLong(2L);
                this.file.writeLong(0L);

            }
        }
    }

    public void showInsert() throws IOException {
        this.file.seek(0);
        for (long i = 0; i < this.file.length()/8; i++) {
            System.out.print(this.file.readLong());
        }
        System.out.println("-------------");
    }

    public long getByteLength(){
        return (long) this.string.getBytes().length;
    }
    public long getStringLength(){
        return (long) this.string.length();
    }
    public String getString(){
        return this.string;
    }
}
