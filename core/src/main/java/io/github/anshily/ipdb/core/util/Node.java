package io.github.anshily.ipdb.core.util;

public class Node {
    /**
     * 父节点
     */
    Link pNode;

    int nodeId;

    /**
     * 节点阶数 大于2
     */
    int m;

    /**
     * 数据链当前指针
     */
    int c;

    /**
     * 数据链
     */
    Data[] data;

    Node(){
        this(new Link());
    }

    Node(Link p){
        this(3,p);
    }

    Node(int nodeId){
        this();
        this.nodeId = nodeId;
    }

    Node(int m, Link p){
        this.m = m;
        this.pNode = p;
        this.data = new Data[m];
        for (int i = 0; i < this.m; i++) {
            this.data[i] = new Data();
        }
    }

    /**
     * 插入数据
     * 更新指针
     */
    public void insert(Data data,Tree pTree){
        for (int i = 0; i < this.data.length; i++) {
            if (this.data[i].empty){
                this.data[i] = data;
                this.data[i].setEmpty(false);
                this.c = i;
                return;
            }else if (this.data[i].compare(data) == 1){
//                若有左子节点插入左子节点
                if (!this.data[i].lc.isEmpty()){
                    pTree.current = this.data[i].lc.getPoint();
                    pTree.insert(data);
                    return;
                }
                for (int j = m-1; j > i; j--) {

                    if (this.data[j].isEmpty() && !this.data[j-1].isEmpty()){
                        this.c = j;
                    }
                    this.data[j] = this.data[j-1];
                }
                this.data[i] = data;
                this.data[i].setEmpty(false);
                return;
            }else if ((this.data[i].compare(data) == -1)){
                //  若有右子节点插入右子节点
                if (!this.data[i].rc.isEmpty()){
                    pTree.current = this.data[i].rc.getPoint();
                    pTree.insert(data);
                    return;
                }
            }else if (this.data[i].compare(data) == 0){
                return;
            }
        }
    }

    /**
     * 数据链中值
     */
    public int middle(){
        return m/2;
    }

    public Data remove(int i){
        Data data1 = data[i];
        data[i] = new Data();
        return data1;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
}
