package io.github.anshily.ipdb.core.util;

public class Tree {
    /**
     * 节点数组
     */
    Node[] nodes;

    /**
     * 当前节点索引
     */
    int current;

//    节点格式
    int nl;

    public Tree(){
        this.nl = 1;
        this.current = 0;
        this.nodes = new Node[10000];
        for (int i = 0; i < this.nodes.length; i++) {
            this.nodes[i] = new Node(i);
        }
    }

    /**
     * 插入数
     * 若当前节点数据已满
     * 则分裂出两个子节点
     * 否则插入数据
     * 1）根据要插入的key的值，找到叶子结点并插入。
     *
     * 2）判断当前结点key的个数是否小于等于m-1，若满足则结束，否则进行第3步。
     *
     * 3）以结点中间的key为中心分裂成左右两部分，然后将这个中间的key插入到父结点中，
     * 这个key的左子树指向分裂后的左半部分，这个key的右子支指向分裂后的右半部分，然后将当前结点指向父结点，继续进行第3步。
     */
    public void insert(Data data){

//        1）根据要插入的key的值，找到叶子结点并插入。
        nodes[current].insert(data,this);

//        2）判断当前结点key的个数是否小于等于m-1，若满足则结束，否则进行第3步。
        if (nodes[current].c < nodes[current].m-1){
            this.current = nodes[current].pNode.getPoint();
            return;
        }else {

//            3）以结点中间的key为中心分裂成左右两部分，然后将这个中间的key插入到父结点中，
//            这个key的左子树指向分裂后的左半部分，这个key的右子支指向分裂后的右半部分，然后将当前结点指向父结点，继续进行第3步。

            Data mdata = nodes[current].remove(nodes[current].middle());

            for (int i = 0; i < nodes[current].data.length; i++) {
                if (!nodes[current].data[i].isEmpty() && nodes[current].data[i].compare(mdata) == -1){
                    nodes[nextNoNullid()].insert(nodes[current].remove(i),this);
                }else if (!nodes[current].data[i].isEmpty() && nodes[current].data[i].compare(mdata) == 1){
                    nodes[nextNoNullid()+1].insert(nodes[current].remove(i),this);
                }else {
                    continue;
                }
            }

            nodes[nextNoNullid()].pNode = new Link(nodes[current].pNode.getPoint());
            nodes[nextNoNullid()+1].pNode = new Link(nodes[current].pNode.getPoint());

            mdata.lc = new Link(nextNoNullid());
            mdata.rc = new Link(nextNoNullid()+1);

            this.nl += 2;

            nodes[nodes[current].pNode.getPoint()].insert(mdata,this);
        }

    }

    public int nextNoNullid(){
        return this.nl;
    }
}
