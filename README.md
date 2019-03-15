# ipdb

### 更新子模块
- 初次运行需要初始化子模块 git submodule update --init --recursive
- 之后执行即可更新子模块 git submodule update
### 编译 go-ipfs

- 首先安装 golang (Download Go 1.11+)[https://golang.org/dl/]
- 进目录 go-ipfs 

### git submodule 简单用法
- git submodule add https://github.com/anshily/go-ipfs.git go-ipfs 
添加子模块到本仓库 git submodule add <url> <path>（url为仓库地址，path为该子模块存储的目录路径）


除此之外B+树还有以下的要求。

1）B+树包含2种类型的结点：内部结点（也称索引结点）和叶子结点。根结点本身即可以是内部结点，也可以是叶子结点。根结点的关键字个数最少可以只有1个。

2）B+树与B树最大的不同是内部结点不保存数据，只用于索引，所有数据（或者说记录）都保存在叶子结点中。

3） m阶B+树表示了内部结点最多有m-1个关键字（或者说内部结点最多有m个子树），阶数m同时限制了叶子结点最多存储m-1个记录。

4）内部结点中的key都按照从小到大的顺序排列，对于内部结点中的一个key，左树中的所有key都小于它，右子树中的key都大于等于它。叶子结点中的记录也按照key的大小排列。

5）每个叶子结点都存有相邻叶子结点的指针，叶子结点本身依关键字的大小自小而大顺序链接。


### 数据结构

每个节点 4k 大小
	内部节点

	{
		"type": 1, // 1、内部节点 2、叶子节点
		"degree": 10, // nodes的度
		"header":
			{
				"size": 10, // 节点大小 单位字节
				"offset": 10000, // 字节偏移量
				"address": "0xfffff" // 文件名
			},
		"nodes": [
			{ 	
				"index": 10,
				"data":"10",
				"link":{
					"size": 10,
					"offset": 10000,
					"address": "0xfffff"
				}
			}
		]
	}

	叶子节点





### 引用
[B树和B+树的插入、删除图文详解](https://www.cnblogs.com/nullzx/p/8729425.html)