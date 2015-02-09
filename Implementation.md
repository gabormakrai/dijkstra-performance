Implementation of Dijkstra's algorithm
===============================

In this framework, there are a couple of different implementations of the algorithm, but they can be classified into three different groups:
* Naive implementation
* Binary Tree Priority Queue implementation
* Fibonacci Heap Priority Queue implementation

### Naive implementation

It is obviously the slowest version (algorithmically), however the results of this version can be useful for validating the other implementation correctness. Also it is interesting to see how this simple version is performing against other advanced implementation. The simplicity of this implementation is giving the chance to the Java VM to optimize the code and also it is giving the opportunity to the hardware architecture for low-level optimization (for example banch prediction).

The actual implementation can be found here: [BaseDijsktra](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/base/BaseDijkstra.java)

### Binary Tree Priority Queue implementation

As mentioned before, it is possible to use Priority Queues to speed up the algorithm. There is a built-in binary search tree ([TreeSet](http://docs.oracle.com/javase/7/docs/api/java/util/TreeSet.html), which is based on [TreeMap](http://docs.oracle.com/javase/7/docs/api/java/util/TreeMap.html) which is a red-back binary search tree) in Java and the framework uses this search tree for the Priority Queue.

Implementation of the Dijkstra's algorithm uses Priority Queue can be found here: [PriorityQueueDijkstra](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/priority/PriorityQueueDijkstra.java)

Implementation of the TreeSet Priority Queue can be found here: [TreeSetPriorityQueue](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/priority/impl/TreeSetPriorityQueue.java)

### Fibonacci Heap Priority Queue implementation

