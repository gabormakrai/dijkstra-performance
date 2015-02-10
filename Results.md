Results
===============================

The computer and runtime environment which was executing the tests has the following properties:
* [Intel Core i7-4770K CPU](http://ark.intel.com/products/75123/Intel-Core-i7-4770K-Processor-8M-Cache-up-to-3_90-GHz)
* 32 GB memory
* Java 1.7.0_51 JDK
* Windows 7 64bit SP1 OS

As described before, the framework has different implementations of the Dijkstra's algorithm:
* Naive implementation
* Priority Queue with the built-in TreeSet binary heap
* Priority Queue with Neo4j's Fibonacci heap
* Priority Queue with Nutch's Fibonacci heap 
* Priority Queue with Teneighty's Fibonacci heap
* Priority Queue with Keithschwarz's Fibonacci heap
* Unfortunately there were two implementations of the Fibonacci heap which produced unexpected exceptions during the tests
  * Growing with the web's Fibonacci heap
  * Pengyifan's Fibonacci heap

For the first run, I was curious about the implementations' runtime on different graph sizes. The framework was executing the following file to find the right answer: [GraphSizeAnalysisMain](https://github.com/gabormakrai/dijkstra-performance/blob/master/DijkstraPerformance/src/dijkstra/main/GraphSizeAnalysisMain.java). It is measuring the runtime depends on different sizes (from n = 10 to n = 1000 in steps of 10) and connectivity (from p = 0.1 to p = 0.9 in steps of 0.2). The following figures contain the results:

<table style="align: center;">
<tr>
<td>
<img src="https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/size_10_1000_01.png" widht="340" height="200" />
</td>
<td>
<img src="https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/size_10_1000_03.png" widht="340" height="200" />
</td>
<td>
<img src="https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/size_10_1000_05.png" widht="340" height="200" />
</td>
</tr>
<tr>
<td>Test case with p = 0.1</td>
<td>Test case with p = 0.3</td>
<td>Test case with p = 0.5</td>
</td>
</tr>
</table>

<table style="align: center;">
<tr>
<td>
<img src="https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/size_10_1000_07.png" widht="340" height="200" />
</td>
<td>
<img src="https://github.com/gabormakrai/dijkstra-performance/blob/master/Result/size_10_1000_09.png" widht="340" height="200" />
</td>
</tr>
<tr>
<td>Test case with p = 0.7</td>
<td>Test case with p = 0.9</td>
</td>
</tr>
</table>

The absolute winner was the Priority Queue version with Teneight's Fibonacci heap and all the Fibonacci heap Priority Queue implementation beats the other two. It is also interesting to see that in a spare graph, the naive implementation was overperformed by all the others implementation, but in a well connected graph, the naive implementation beats Priority Queue with binary heap implementation.

