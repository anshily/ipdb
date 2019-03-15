package io.github.anshily.ipdb.core.tree;


public class BtreeModify {
    //表示这棵BTree是m阶，每个节点最多M棵子树，M-1个关键字
    private static final int M;
    //每个节点至少MIN_KEY_NUM个子树，MIN_KEY_NUM - 1 个关键字
    private static final int MIN_KEY_NUM;

    private static BTreeNode root;

    static{
        M = 3;
        //注意是2.0，而不是2
        MIN_KEY_NUM = (int)Math.ceil(M/2.0);
    }
    public void createBTree(KeyNode[] arr){

        for(int i = 0; i < arr.length ; i ++){
            //System.out.println(i);
            add(arr[i]);
        }
    }

    public void add(KeyNode key){
        //根为空
        if(root == null){
            root = new BTreeNode();
            root.keys[++root.keyNum] = key;
            root.parrent = null;
            return;
        }
        BTreeNode p = root;
        label:
        while(true){

            //插入排序
            int i = 0;
            for(i = p.keyNum; i >= 1 ; i--){
                //找寻插入点
                if(key.k > p.keys[i].k){
                    break;
                }
                if(key.k == p.keys[i].k){
                    return;
                }
            }
            if(p.childs[i] == null){
                //插入点为i+1
                for(int j = p.keyNum ; j >= (i + 1); j--){
                    p.keys[j + 1] = p.keys[j];
                }

                p.keys[i + 1] = key;
                p.keyNum++;
                break label;
            }else{
                p = p.childs[i];
            }
        }
        //将key插入p之后，判断p节点是否符合BTree的条件
        //每个节点的关键字最多M-1个
        if(p.keyNum > M - 1){
            //将p节点分裂
            split(p);
        }
    }

    private void split(BTreeNode p){
        BTreeNode lt = new BTreeNode();
        BTreeNode gt = new BTreeNode();
        //分裂出来的左节点
        /*
         *   M = 3
         *   MIN_KEY_NUM = 2
         *   keys:
         *   -----------------
         *   |0  |1  |2  |3  |
         *   -----------------
         *   childs:
         *   -----------------
         *   |0  |1  |2  |3  |
         *   -----------------
         *   index = M 的值作为备用
         */

        System.arraycopy(p.keys,1,lt.keys,1,MIN_KEY_NUM - 1);
        System.arraycopy(p.childs,0,lt.childs,0,MIN_KEY_NUM);
        lt.keyNum = MIN_KEY_NUM - 1;
        //System.out.println(p.keys[MIN_KEY_NUM] + ":" + Arrays.toString(lt.keys));

        System.arraycopy(p.keys,MIN_KEY_NUM + 1,gt.keys,1,M - MIN_KEY_NUM);
        System.arraycopy(p.childs,MIN_KEY_NUM,gt.childs,0,M - MIN_KEY_NUM + 1);
        gt.keyNum = M - MIN_KEY_NUM;
        //System.out.println(p.keys[MIN_KEY_NUM] + ":" + Arrays.toString(gt.keys));
        /*
         *   分隔节点之后，需要更改父节点信息
         */
        for(int i = 0 ; i <= lt.keyNum ; i ++){
            if(lt.childs[i] != null){
                lt.childs[i].parrent = lt;
            }
        }
        for(int i = 0 ; i <= gt.keyNum ; i ++){
            if(gt.childs[i] != null){
                gt.childs[i].parrent = gt;
            }
        }

        BTreeNode parrent = p.parrent;
        //root节点分裂
        if(parrent == null){
            BTreeNode tmp = new BTreeNode();
            tmp.keys[++tmp.keyNum] = p.keys[MIN_KEY_NUM];
            tmp.parrent = null;
            tmp.childs[tmp.keyNum - 1] = lt;
            tmp.childs[tmp.keyNum] = gt;
            lt.parrent = tmp;
            gt.parrent = tmp;
            root = tmp;
        }else{
            int i = 0;
            //System.out.println("parrent:"+Arrays.toString(parrent.keys));
            //插入排序
            for(i = parrent.keyNum; i >= 1 ; i--){
                //找寻插入点
                if(p.keys[MIN_KEY_NUM].k > parrent.keys[i].k){
                    break;
                }
            }
            //插入点为i+1
            for(int j = parrent.keyNum ; j >= (i + 1); j--){
                parrent.keys[j + 1] = parrent.keys[j];
                parrent.childs[j + 1] = parrent.childs[j];
            }
            parrent.keys[i + 1] = p.keys[MIN_KEY_NUM];
            //System.out.println("parrent:"+Arrays.toString(parrent.keys));
            parrent.childs[i] = lt;
            parrent.childs[i + 1] = gt;
            lt.parrent = parrent;
            gt.parrent = parrent;
            parrent.keyNum++;
            //System.out.println("parrent:"+parrent.keyNum);
            //是否继续分裂
            if(parrent.keyNum > M - 1){
                //将p节点分裂
                split(parrent);
            }
        }
    }

    /*
     *   删除节点i中的key
     *   1. 节点i不是最底层非终端节点
     *       将Ai子树上的最小值替换key
     *       问题变为2
     *   2. 节点i是最底层非终端节点
     *       2.1 节点i的关键字个数 >= MIN_KEY_NUM,直接删除
     *       2.2 节点i的关键字个数 = MIN_KEY_NUM - 1
     *           2.2.1 兄弟节点的关键字个数 >= MIN_KEY_NUM,将右兄弟最小值（左兄弟最大值）上移至父节点相应位置的数据，然后将父节点被替换的数据下移至被删除节点
     *           2.2.2 兄弟节点的关键字个数 = MIN_KEY_NUM - 1，合并（借助父节点）
     *
     */
    public static void delete(KeyNode key){
        //System.out.println("deletekey:" + key);
        //遍历BTree,查找key所在的节点
        BTreeNode p = root;
        int index = 0;
        label:
        while(p != null){
            //System.out.println("p.keyNum:" + p.keyNum);
            //System.out.println("p:" + Arrays.toString(p.keys));
            for(int i = p.keyNum; i >= 1; i --){
                if(key == p.keys[i]){
                    index = i;
                    break label;
                }else if(key.k > p.keys[i].k){
                    p = p.childs[i];
                    continue label;
                }
            }
            p = p.childs[0];
        }
        if(p == null){
            return ;
        }
        //System.out.println("p.keyNum:" + p.keyNum);
        //System.out.println("p:" + Arrays.toString(p.keys));
        //找到p
        if(p.childs[0] == null){
            delete(p,key);
        }else{
            //对于不是最底层的非终端节点,则将别比key小的最大值替换key
            BTreeNode q = p.childs[index - 1];
            while(q.childs[0] != null){
                q = q.childs[q.keyNum];
            }
            p.keys[index] = q.keys[q.keyNum];
            delete(q,q.keys[q.keyNum]);
        }
    }
    public static void delete(BTreeNode p, KeyNode key){
        if(p.keyNum >= MIN_KEY_NUM){
            //删除后节点关键字数满足MIN_KEY_NUM - 1,直接删除
            for(int i = 1; i <= p.keyNum; i ++){
                if(p.keys[i].k > key.k){
                    p.keys[i - 1] = p.keys[i];
                }
            }
            p.keys[p.keyNum].k = 0;
            p.keyNum--;
        }else{
            //删除后节点关键字数不能够满足MIN_KEY_NUM - 1,可能需要合并
            int index = 0;
            BTreeNode q = p.parrent;
            if(q == null){
                //p是root，直接删除，因为root至少两棵子树，所以关键字个数没有限制
                for(int i = 1; i <= p.keyNum; i ++){
                    if(p.keys[i].k > key.k){
                        p.keys[i - 1] = p.keys[i];
                    }
                }
                p.keys[p.keyNum].k = 0;
                p.keyNum--;
                return;
            }
            for(int i = 0; i <= q.keyNum ; i ++){
                if(q.childs[i] == p){
                    index = i;
                    break;
                }
            }
            //查询右兄弟并且右兄弟的关键字个数大于MIN_KEY_NUM - 1,
            //将父节点中第一个大于key的值移动到删除节点
            //将右兄弟中的最小值替换父节点中的第一个大于key的值
            if(index < q.keyNum && q.childs[index + 1].keyNum >= MIN_KEY_NUM){
                for(int i = 0 ; i <= p.keyNum ; i ++){
                    if(p.keys[i].k > key.k){
                        p.keys[i - 1] = p.keys[i];
                    }
                }
                p.keys[p.keyNum] = q.keys[index + 1];
                q.keys[index + 1] = q.childs[index + 1].keys[1];
                for(int i = 2; i <= q.childs[index + 1].keyNum ; i ++){
                    q.childs[index + 1].keys[i - 1] = q.childs[index + 1].keys[i];
                }
                q.childs[index + 1].keys[q.childs[index + 1].keyNum].k = 0;
                q.childs[index + 1].keyNum--;
                return;
            }
            //查询左兄弟并且左兄弟的关键字个数大于MIN_KEY_NUM - 1
            //将父节点中最后一个小于key的值移动到删除节点
            //将左兄弟中的最大值替换父节点中的最后一个小于key的值
            if(index > 0 && q.childs[index - 1].keyNum >= MIN_KEY_NUM){

                for(int i = p.keyNum ; i >= 1  ; i --){
                    if(p.keys[i].k < key.k){
                        p.keys[i + 1] = p.keys[i];
                    }
                }
                p.keys[1] = q.keys[index];
                q.keys[index] = q.childs[index - 1].keys[q.childs[index - 1].keyNum];
                q.childs[index - 1].keys[q.childs[index - 1].keyNum].k = 0;
                q.childs[index - 1].keyNum--;
                return;
            }

            //不符合上述条件，则需要有merge操作
            //删除key,然后和兄弟合并
            for(int i = 1 ; i < p.keyNum; i++){
                if(p.keys[i].k > key.k){
                    p.keys[i - 1] = p.keys[i];
                }
            }
            //将最后一个值赋值为0
            p.keys[p.keyNum].k = 0;
            p.keyNum--;
            if(index < q.keyNum){
                //将节点与右节点结合
                merge(p,true);
            }else{
                //将节点与左节点结合
                merge(p,false);
            }
        }
    }
    public static void merge(BTreeNode p, boolean flag){
        BTreeNode q = p.parrent;
        int index = 0;
        for(int i = 0; i <= q.keyNum ; i++){
            if(q.childs[i] == p){
                index = i;
            }
        }
        if(flag){
            //将节点与右节点结合
            BTreeNode br = q.childs[index + 1];
            //System.out.print("br.keyNum:" + br.keyNum);
            //System.out.println("\t\t\t" + Arrays.toString(br.keys));
            p.keys[++p.keyNum] = q.keys[index + 1];

            for(int i = index + 1; i <= q.keyNum; i++){
                q.keys[i] = q.keys[i + 1];
                q.childs[i] = q.childs[i + 1];
            }
            q.keyNum --;
            //if(br.keyNum > 0){
            System.arraycopy(br.keys,1,p.keys,p.keyNum + 1,br.keyNum);
            System.arraycopy(br.childs,0,p.childs,p.keyNum,br.keyNum + 1);
            p.keyNum += br.keyNum;
            //}

            if(q == root){
                if(q.keyNum == 0){
                    root = p;
                    p.parrent = null;
                    for(int i = 0; i <= p.keyNum; i ++){
                        if(p.childs[i] != null){
                            p.childs[i].parrent = root;
                        }
                    }
                }
            }else{
                if(q.keyNum < MIN_KEY_NUM - 1){
                    BTreeNode q1 = q.parrent;
                    int index1 = 0;
                    for(int i = 0; i <= q1.keyNum ; i++){
                        if(q1.childs[i] == q){
                            index1 = i;
                        }
                    }
                    if(index1 < q1.keyNum){
                        merge(q,true);
                    }else{
                        merge(q,false);
                    }
                }
            }
        }else{
            //将节点与左节点结合
            BTreeNode lr = q.childs[index - 1];
            merge(lr,true);
        }
    }


    public static class BTreeNode
    {
        //指向父节点指针
        public BTreeNode parrent;
        //本节点的关键字个数
        public int keyNum;
        // 数据链
//        public String[] data = new String[M + 1];
        //关键字数组,最多M-1个关键字，0号元素不用，1个作为备用
//        public int[] keys = new int[M + 1];
        public KeyNode[] keys = new KeyNode[M +1];
        //子树指针，最多M棵子树
        public BTreeNode[] childs = new BTreeNode[M + 1];
    }

    public static class KeyNode{
        int k;
        String v;
        KeyNode(int k,String s){
            this.k = k;
            this.v = s;
        }
    }
}
