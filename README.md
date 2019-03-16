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

### 描述

	项目起源于笔者的一个 idea ———— 数据上链，因此需要一个去中心化的存储系统，最终选型 ipfs 但是 ipfs 上数据的索引为 hash 不方便记忆和传播，而平常项目里的数据库提供了便捷的数据存储操作，于是诞生了自己写一个简单的数据库的想法。

###### 技术选型
  基于ipfs的 hash 为定长数据 因此选择 b-tree 建立hash的索引就可以实现一个基本的数据库索引
  检索了很多 b-tree 的文章 这里将自己的一些心得给记录下来 

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
- [B树和B+树的插入、删除图文详解](https://www.cnblogs.com/nullzx/p/8729425.html)
- [数据结构与算法：B树（B-Tree）定义及搜索、插入、删除基本操作](https://blog.csdn.net/u014165620/article/details/82976882)
- [从 MongoDB 及 Mysql 谈B/B+树](https://blog.csdn.net/wwh578867817/article/details/50493940)
- [btree 可视化](https://www.cs.usfca.edu/~galles/visualization/BTree.html)