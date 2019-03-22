package io.github.anshily.ipdb.core.btree;

import io.github.anshily.ipdb.core.util.StoreHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static io.github.anshily.ipdb.core.base.Constants.MINMUM_DEGREE;

/**
 * Btree 大概实现思路
 * 叶节点直接插入
 *      然后判断是否已满
 *      满则提升
 *
 * 内部节点寻找到叶节点 执行叶节点的插入
 *      满则提升
 *
 * 没有孩子的根节点的插入
 *      // 若 根节点为空 新建根节点 通常在初始化时候 创建根节点
 *      非空 同叶节点的插入
 * 有孩子的根节点的插入
 *      同内部节点
 * 节点提升
 *      找到节点的中间值
 *
 *
 *      根节点提升
 *
 *      new 一个新的内部节点 r1 为根节点
 *      new 一个新的内部节点 n1 获取满节点的后部分 key 和 child 即 cc2 ck2 cc3 ...
 *
 *      将 r1c1 指向 n1 将 ck0 提升 r1k1 将 r1c0 指向原根节点 原根节点中 中ck1及之后关键字及指针弹出
 *
 *      内部节点提升
 *      pc0 pk0 pc1 pk1
 *
 *      cc0 ck0 cc1 cck1 cc2 ck2 cc3
 *
 *      new 一个新的内部节点 n1 获取满节点的后部分 key 和 child 即 cc2 ck2 cc3 ...
 *
 *      若中间 key cck1 < pk0
 *          将 pk0 pc1 pk1 pc2 后移 即 >> pk1 pc2 pk2 pc3
 *          将 pc1 指向 n1  将 ck1 提升为pk0 原pc0 中ck1及之后关键字及指针弹出
 *
 *      叶节点提升
 *
 *          new 一个新的叶节点 n1 获取满节点的后半部分 仅key ck2
 *
 *          若 中间key ck1 < pk0
 *              将 pk0 pc1 pk1 pc2 后移 即 >> pk1 pc2 pk2 pc3
 *              将 pc1 指向n1 将 ck1 提升为pk0 原pc0 中ck1及之后关键字弹出
 *
 *          //  插入父节点时 若从左到右对比 则此种情况不会出现
 *          //  若 ck1 > pk0
 *          //  将 pk1 pc2 后移 pc1 指向n1
 *
 *          判断父节点 pn 是否已满
 *              满则 pn 递归节点提升
 *
 *      pc0 pk0 pc1 pk1 pc2
 *
 *      ck0 ck1 ck2
 *
 */
public class Btree {
    ArrayList<Node> storeBase;
    int rid;
    int storeBaseTail;
    int t;

    Btree(){
        this.t = MINMUM_DEGREE;
        this.storeBaseTail = 0;
        this.rid = 0;
        this.storeBase = new ArrayList<Node>();
        Node node = new Node();
//        node.leaf = false;
        updataBaseStroe(node);
    }

    /**
     * 插入数据
     * @param data
     */
    public void insert(Data data) {
        Node rNode = this.storeBase.get(this.rid);
        insert(rNode,data);
    }

    /**
     * 树内部数据的插入
     * @param node
     * @param data
     */
    private void insert(Node node, Data data){
        // 叶节点的插入
        if (node.leaf){
            leafInsert(node,data);
        }else {
            innerInstert(node,data);
        }
    }

    /**
     * 提升节点
     * @param node
     */
    private void promote(Node node){
 /**      叶节点提升
 *          new 一个新的叶节点 n1 获取满节点的后半部分 仅key ck2
 *
 *          若 中间key ck1 < pk0
 *              将 pk0 pc1 pk1 pc2 后移 即 >> pk1 pc2 pk2 pc3
 *              将 pc1 指向n1 将 ck1 提升为pk0 原pc0 中ck1及之后关键字弹出
 *
 *          //  插入父节点时 若从左到右对比 则此种情况不会出现
 *          //  若 ck1 > pk0
 *          //  将 pk1 pc2 后移 pc1 指向n1
 *
 *          判断父节点 pn 是否已满
 *              满则 pn 递归节点提升
 *
 *      pc0 pk0 pc1 pk1 pc2
 *
 *      ck0 ck1 ck3  t=2
 */
        if (node.leaf){
//            根为叶节点
            if (this.rid == node.nodeId){
//                new 一个非叶节点 作为新根
                Node pNode = this.allocateNode();
                pNode.leaf = false;
                this.rid = pNode.nodeId;

//                new 一个新节点作为 右孩子
                Node cNode = this.allocateNode();
                cNode.parentId = pNode.nodeId;
                node.parentId = pNode.nodeId;
//                cNode.leaf = false;
//                将左孩子的后半部分 赋给右孩子
                for (int i = 2*t-2; i > t-1; i--) {
                    cNode.key.add(0,node.key.get(i));
                    node.key.remove(i);
                }
//                for (int i = 2*t-1; i > t-1; i--) {
//                    cNode.child.add(0,node.child.get(i));
//                    node.child.remove(i);
//                }
                //            中间key t-1
                Data data = node.key.get(t-1);
                node.key.remove(t-1);

//                此时 父节点为空 将将中间值作为 key0
//                更新左右孩子
                pNode.updataNodeKey(0,data);
                pNode.updataNodeChild(0,node.nodeId);
                pNode.updataNodeChild(1,cNode.nodeId);
//              在 t >= 2的情况下 必不会满 此处仅为冗余判断
                if (isFull(pNode)){
                    promote(pNode);
                }else {
                    this.updataBaseStroe(pNode);
                }
                this.updataBaseStroe(node);
                this.updataBaseStroe(cNode);

            }else {
//                叶节点的提升 new 一个右孩子 自己成为左孩子
                Node cNode = this.allocateNode();
                cNode.parentId = node.parentId;

//                将叶节点 的后部分转移给 新的叶节点
                for (int i = 2*t-2; i > t-1; i--) {
                    cNode.key.add(0,node.key.get(i));
                    node.key.remove(i);
                }
//                非根叶节点必有父
                Node pNode = this.storeBase.get(node.parentId);
//            获取中间key t-1 中的数据
                Data data = node.key.get(t-1);
//                移除中间 key
                node.key.remove(t-1);

                Integer pSize = pNode.key.size();
//            找到插入节点的位置并后移其后节点 用 ArrayList的 add(i,data) 函数可以实现插入功能
                for (int i = 0; i < pSize; i++) {
//                找到插入点i
                    if (pNode.key.get(i).keyGt(data)){
                        // 方法1
//                    for (int j = i; j < pSize; j++) {
//                        pNode.updataNodeKey(j+1,pNode.key.get(j));
//                        pNode.updataNodeChild(j+2,pNode.child.get(j+1));
//                    }
//                    pNode.updataNodeChild(i+1,cNode.nodeId);
//                    pNode.updataNodeChild(i,node.nodeId);
//                    pNode.updataNodeKey(i,data);
//                    return;

//                    方法2
                        pNode.key.add(i,data);
                        pNode.child.add(i+1,cNode.nodeId);

                        System.out.println("方法2");
                        printNode(pNode);

                        if (isFull(pNode)){
                            promote(pNode);
                        }else {
                            this.updataBaseStroe(pNode);
                        }

                        this.updataBaseStroe(node);
                        this.updataBaseStroe(cNode);

                        return;
                    }
                }
//            插入队尾
//            pNode.updataNodeChild(pSize,cNode.nodeId);
//            pNode.updataNodeKey(pSize - 1,data);
                pNode.child.add(cNode.nodeId);
                pNode.key.add(data);

                System.out.println("方法3");
                printNode(pNode);

                if (isFull(pNode)){
                    promote(pNode);
                }else {
                    this.updataBaseStroe(pNode);
                }

                this.updataBaseStroe(node);
                this.updataBaseStroe(cNode);

            }

        }else {
            /**
             *                                          3
             *
             *       1    3    5                   1         5
             *                         >>
             *    0     2   4     6              0   2     4   6
             *
             *
             *    内部节点的提升
             */
            if (this.rid == node.nodeId){

//                生产一个新根 pNode
                Node pNode = this.allocateNode();
                pNode.leaf = false;
                this.rid = pNode.nodeId;

                node.parentId = pNode.nodeId;

//                生成一个左孩子 cNode
                Node cNode = this.allocateNode();
                cNode.parentId = pNode.nodeId;
                cNode.leaf = false;

//                node 的右半部分 赋给新右孩子
                for (int i = 2*t-2; i > t-1; i--) {
                    cNode.key.add(0,node.key.get(i));
                    node.key.remove(i);
                }
//                将 孩子转移给新节点时 孩子的父节点也要更新！！！
                for (int i = 2*t-1; i > t-1; i--) {
                    Node tmp = this.storeBase.get(node.child.get(i));
                    tmp.parentId = cNode.nodeId;
                    cNode.child.add(0,tmp.nodeId);
                    updataBaseStroe(tmp);
                    node.child.remove(i);
                }

                //            中间key t-1
                Data data = node.key.get(t-1);
                node.key.remove(t-1);

                System.out.println("方法4");
                printNode(node);
// pNode是new的新根

//                Integer pSize = pNode.key.size();
//                for (int i = 0; i < pSize; i++) {
////                找到插入点 i
//                    if (pNode.key.get(i).keyGt(data)){
//                        pNode.key.add(i,data);
//                        pNode.child.add(i+1,cNode.nodeId);
//                    }
//                }
//                直接插入key0 c0 c1 即可
                pNode.updataNodeKey(0,data);
                pNode.updataNodeChild(0,node.nodeId);
                pNode.updataNodeChild(1,cNode.nodeId);

                if (isFull(pNode)){
                    promote(pNode);
                }else {
                    this.updataBaseStroe(pNode);
                }

                this.updataBaseStroe(node);
                this.updataBaseStroe(cNode);
            }else {
//                普通内部节点有自己的父
//                因此 生产一个新的内部节点接收 node 的右部 key 和 child
                Node cNode =this.allocateNode();
                cNode.leaf = false;
                cNode.parentId = node.parentId;
//                转移key
                for (int i = 2*t-2; i > t-1; i--) {
                    cNode.key.add(0,node.key.get(i));
                    node.key.remove(i);
                }

//                转移child 此时 child 的父应更新为 cNode
                for (int i = 2*t-1; i > t-1; i--) {
                    Node tmp = this.storeBase.get(node.child.get(i));
                    tmp.parentId = cNode.nodeId;
//                    更新孩子节点的父节点指针

                    cNode.child.add(0,tmp.nodeId);
                    updataBaseStroe(tmp);
                    node.child.remove(i);
                }

                System.out.println("方法5");
                printNode(node);

                Node pNode = this.storeBase.get(node.parentId);
//            中间key t-1
                Data data = node.key.get(t-1);
                node.key.remove(t-1);


                Integer pSize = pNode.key.size();
                for (int i = 0; i < pSize; i++) {
//                找到插入点 i
                    if (pNode.key.get(i).keyGt(data)){

                        pNode.key.add(i,data);
                        pNode.child.add(i+1,cNode.nodeId);
//                        for (int j = i; j < pSize; j++) {
//                            pNode.updataNodeKey(j+1,pNode.key.get(j));
//                            pNode.updataNodeChild(j+2,pNode.child.get(j+1));
//                        }
//                        pNode.updataNodeChild(i+1,cNode.nodeId);
//                        pNode.updataNodeChild(i,node.nodeId);
//                        pNode.updataNodeKey(i,data);

                        System.out.println("方法6");
                        printNode(pNode);

                        if (isFull(pNode)){
                            promote(pNode);
                        }else {
                            this.updataBaseStroe(pNode);
                        }

                        this.updataBaseStroe(node);
                        this.updataBaseStroe(cNode);
                        return;
                    }
                }
//            插入队尾
                pNode.child.add(cNode.nodeId);
                pNode.key.add(data);
//                pNode.updataNodeChild(pSize,cNode.nodeId);
//                pNode.updataNodeKey(pSize - 1,data);

                if (isFull(pNode)){
                    promote(pNode);
                }else {
                    this.updataBaseStroe(pNode);
                }
                this.updataBaseStroe(node);
                this.updataBaseStroe(cNode);
            }
        }
    }

    private void leafInsert(Node node, Data data){
        Integer nSize = node.key.size();
        for (int i = 0; i < nSize; i++) {
            if (node.key.get(i).keyEq(data)){
                node.updataNodeKey(i,data);
                return;
            }else if (node.key.get(i).keyGt(data)){
//                for (int j = i; j < nSize; j++) {
//                    node.updataNodeKey(j+1,node.key.get(j));
//                }

                node.key.add(i,data);
//                node.updataNodeKey(i,data);
                if (isFull(node)){
                    promote(node);
                }else {
                    updataBaseStroe(node);
                }
                return;
            }
        }
//        若未找到插入点则插入队尾
//        node.updataNodeKey(nSize,data);
        node.key.add(data);
        if (isFull(node)){
            promote(node);
        }else {
            updataBaseStroe(node);
        }
    }

    private void innerInstert(Node node, Data data){
        Integer nSize = node.key.size();
        if (node.leaf){
            leafInsert(node,data);
            return;
        }
        for (int i = 0; i < nSize; i++) {
            if (node.key.get(i).keyEq(data)){
                node.updataNodeKey(i,data);
                return;
            }else if (node.key.get(i).keyGt(data)){
//                内部节点需要找到要插入的叶节点
                Integer cid = node.child.get(i);
                Node cNode = this.storeBase.get(cid);
                innerInstert(cNode,data);

                Node node1 = this.storeBase.get(cNode.parentId);
                if (isFull(node1)){
                    promote(node1);
                }else {
                    updataBaseStroe(node1);
                }

                if (isFull(cNode)){
                    promote(cNode);
                }else {
                    updataBaseStroe(cNode);
                }
                return;
            }
        }
        innerInstert(this.storeBase.get(node.child.get(nSize)),data);
    }

    private boolean isFull(Node node){
        if (2*t-1 == node.key.size()){
            return true;
        }else {
            return false;
        }
    }

    private Node allocateNode() {
        Node node = new Node(++this.storeBaseTail);
        this.updataBaseStroe(node);
        return node;
    }

    private void updataBaseStroe(Node node){
        if (storeBase.size() > node.nodeId){
            this.storeBase.set(node.nodeId, node);
        }else {
            this.storeBase.add(node.nodeId, node);
        }
    }

    public void printTree(){
        bfs(this.storeBase.get(rid));
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    private void printNode(Node node){

        System.out.println("------------------ pNode "+ node.parentId +"------------------");
        System.out.print(" |        ");
        for (int i = 0; i < node.key.size(); i++) {
            System.out.print("key"+ i + " : " + node.key.get(i).key+ "    ");
        }
        System.out.println(" | ");
        System.out.print(" | ");
        for (int i = 0; i < node.child.size(); i++) {
            System.out.print("child "+ i + " : " + node.child.get(i) + " | ");
        }
        System.out.println();
        System.out.println("--------- node "+ node.nodeId +"--------leaf:"+ node.leaf +"---------");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void bfs(Node node){
        printNode(node);
        System.out.println();
        for (int i = 0; i < node.child.size(); i++) {
            bfs(this.storeBase.get(node.child.get(i)));
        }
    }

    public void printUnser(){
        StoreChain.stroe(this.storeBase);

        ArrayList<Node> nodes = StoreChain.cat();

        bfs(nodes.get(rid));


    }

    public void printJSONleng(){
        try {
            StoreHelper storeHelper = new StoreHelper(this.storeBase,"obj.json");

            storeHelper.store();

            String string = storeHelper.cat();

            System.out.println(string);

            System.out.println(string.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
