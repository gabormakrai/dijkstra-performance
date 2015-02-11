Data generation and measurement scenarios
===============================

For the data generation, I have created a random graph generator. This generator created random directed graphs according the two parameters:
* size of the graph in the terms of vertices/nodes in the graph (parameter n)
* connectivity of the graph: how sparse is the graph (parameter p)
* the connectivity is expressed by a 0.0 <= p <= 1.0 floating number where 0.0 means there is no edge/arc in the graph and 1.0 means it is a complete graph.

Here is just a simple example of the graph generation method with different connectivity parameter: (online version of the  [Graphviz](http://graphviz-dev.appspot.com/) was used to generate these graph using the output of this simple program: [GraphGeneratorDotOutputMain](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/main/GraphGeneratorDotOutputMain.java), [Output text file](https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/graphgeneratordotoutput.txt))

<table>
<tr>
<td><img src="https://raw.githubusercontent.com/gabormakrai/dijkstra-performance/master/Result/graph_10_01.png" height="200" width="218"/></td>
<td><img src="https://raw.githubusercontent.com/gabormakrai/dijkstra-performance/master/Result/graph_10_03.png" height="200" width="218"/></td>
</td>
<td><img src="https://raw.githubusercontent.com/gabormakrai/dijkstra-performance/master/Result/graph_10_05.png" height="200" width="218"/></td>
</td>
<td><img src="https://raw.githubusercontent.com/gabormakrai/dijkstra-performance/master/Result/graph_10_09.png" height="200" width="218"/></td>
</td>
</tr>
<tr>
<td>n=10,p=0.1</td>
<td>n=10,p=0.3</td>
<td>n=10,p=0.5</td>
<td>n=10,p=0.9</td>
</tr>
</table>

The generation method has two phases:
* In the beginning, it is creating a random directed spanning tree to make sure that the graph is fully connected. (we can reach every node from other node).
* The second phase is the random arcs/edges addition phase, where random arcs/edges are added until the necessary connectivity rate has been reached.

The framework implements measurement scenarios in the way that different algorithms can be compared to each other in the fairest way. We are only considering the calculation of the previous/distance vector, which is the core for the Dijkstra's algorithm. For each experiment, the framework creates a random graph and runs 20 random vector generation from different origins. Only the runtime of the vector calculation is measured. This process is repeated 20 times and the 3 worst and best runtime has been dropped (to avoid outlier runtimes) and an average runtime is calculated from the remaining 14 runtimes.
