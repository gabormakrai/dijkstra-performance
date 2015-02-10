Implementation details
===============================

In this framework, there are a couple of different implementations of the algorithm, but they can be classified into three different groups:
* Naive implementation
* Binary Tree Priority Queue implementation
* Fibonacci Heap Priority Queue implementation

Before I introduce the implementation details of the algorithms, I will introduce how the framework is implementing the graph. I coded the [neighbourhood list approach](http://en.wikipedia.org/wiki/Adjacency_list) where two nested arrays represent the actual graph: there is a int[][] which is storing the neighbour nodes for each node and a double[][] which is storing the corresponding arc/edge weights.

### Naive implementation

It is obviously the slowest version (algorithmically), however the results of this version can be useful for validating the other implementation correctness. Also it is interesting to see how this simple version is performing against other advanced implementation. The simplicity of this implementation is giving the chance to the Java VM to optimize the code and also it is giving the opportunity to the hardware architecture for low-level optimization (for example banch prediction).

The actual implementation can be found here: [BaseDijsktra](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/base/BaseDijkstra.java)

### Binary Tree Priority Queue implementation

As mentioned before, it is possible to use Priority Queues to speed up the algorithm. For this and the other Priority Queue style algorithms, I created a general code to test them. This version of the Dijkstra can be found here: [PriorityQueueDijkstra](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/priority/PriorityQueueDijkstra.java) For every single PriorityQueue algorithm, there is a wrapper class which is using the heap algorithm inside to represent the Priority Queue. The interface for it can be found here: [PriorityQueue](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/priority/PriorityQueue.java). There is a built-in binary search tree ([TreeSet](http://docs.oracle.com/javase/7/docs/api/java/util/TreeSet.html), which is based on [TreeMap](http://docs.oracle.com/javase/7/docs/api/java/util/TreeMap.html) which is a red-back binary search tree) in Java and the framework uses this search tree for the Priority Queue. 

Implementation of the TreeSet Priority Queue can be found here: [TreeSetPriorityQueue](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/priority/impl/TreeSetPriorityQueue.java)

### Fibonacci Heap Priority Queue implementation

Instead of trying to implement myself the Fibonacci heap, I tried to find existing open-source implementations. After reading through many pages of the Google search "Fibonacci heap Java" I could find the following ones:

* [Teneigty's implementation](https://code.google.com/p/java-heaps/source/browse/trunk/src/main/java/org/teneighty/heap/FibonacciHeap.java?r=39)
* [Neo4j's implementation](https://github.com/neo4j/neo4j/blob/master/community/graph-algo/src/main/java/org/neo4j/graphalgo/impl/util/FibonacciHeap.java)
* [Pengyifan's implementation](https://github.com/yfpeng/pengyifan-commons/tree/master/src/main/java/com/pengyifan/commons/collections/heap)
* [GrowingTheWeb's implementation](http://www.growingwiththeweb.com/2014/06/fibonacci-heap.html)
* [Keithschwarz's implementation](http://www.keithschwarz.com/interesting/code/?dir=fibonacci-heap)
* [Nutch's implementation](https://gitorious.org/discovered/repo/source/1d47935e78072239bffc39c9436ade75f8e273b6:src/java/org/apache/nutch/util/FibonacciHeap.java)

