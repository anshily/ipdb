package io.github.anshily.ipdb.core.btree;

import java.util.ArrayList;

import static io.github.anshily.ipdb.core.base.Constants.MINMUM_DEGREE;

/**
 * Btree 大概实现思路
 * 叶节点直接插入
 *      然后判断是否已满
 *      满则提升
 *
 * 内部节点寻找到叶节点 执行叶节点的插入
 *
 * 节点提升
 *      找到节点的中间值
 *
 *
 *
 *      内部节点提升
 *      c0 k0
 *
 *      c0 k0 c1 k1 c2 k2 c3
 *
 *      叶节点提升
 *
 *          若 ck1 < pk0
 *              将 pk0 pc1 pk1 pc2 后移
 *
 *          若 ck1 > pk0 
 *              将 pk1 pc2 后移
 *
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
        updataBaseStroe(node);
    }

    /**
     * 插入节点
     * @param node
     */

    /**
     * 提升节点
     * @param node
     */

    public void updataBaseStroe(Node node){
        if (storeBase.size() > node.nodeId){
            this.storeBase.set(node.nodeId, node);
        }else {
            this.storeBase.add(node.nodeId, node);
        }
    }
}
