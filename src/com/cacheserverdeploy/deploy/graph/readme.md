**这里对每个类的代码，以及功能进行简要的说明**


Vertex.java：顶点类
===

Edge.java ：有向类
===

- 成员变量weight：表示单位流量的费用
- 成员变量capacity：表示这条边的残存容量
- 成员变量f: 保存边的初始容量，最大流fordFulkerson算法会改变capacity，
- 所以在求出最大流后用f去回复capacity的值。方便服务器位置改变后，再次使用fordFulkerson算法

LinkGraph.java :邻接链表类
===

MaxFlow.java:最大流类
===

- 只有一个邻接链表类的成员变量lGraph，用于表示给定的网络。
-包含了一个关键的算法：fordFulkerson(int s,int t),用于求解给定网络的最大流。

MinPath.java :最短路径类
===

优先队列类.java :优先队列类
===

自定义一个优先队列类的原因是：由于官方的最小优先队列没有实现减小某一个顶点的值
（为了保持最小优先队列的性质，减小顶点的值后必须进行堆调整，最小优先队列是用数据结构堆实现的）。
在最短路径dijkstra算法中需要减小队列中某些顶点的距离。

