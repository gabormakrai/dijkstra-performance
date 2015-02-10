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
  
For the first run, I was curious about the implementations runtime of different graph sizes

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

