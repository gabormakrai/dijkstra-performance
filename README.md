Framework for testing Dijkstra's algorithm
===============================

This project aims to measure the performance of different implementation of one of the most known single source shortest path algorithm, Dijkstra's shortest path algorithm. I am considering the naive, priority queue with binary heap and priority queue with Fibonacci heap type implementations where I am using existing open-source implementation of the Fibonacci heap. My goal with this experiment is to show different implementation's strength. All the theoritical knowledge can be found in [CLRS](http://www.amazon.co.uk/Introduction-Algorithms-T-Cormen/dp/0262533057) 24.3 and [CLRS](http://www.amazon.co.uk/Introduction-Algorithms-T-Cormen/dp/0262533057) 19. To get deeper understanding on the measurement, I have decided to split the text into 4 parts:

* [All about Dijkstra's algorithm](https://github.com/gabormakrai/dijkstra-performance/blob/master/Dijkstra.md)
* [Implementation details of the different Dijkstra's versions](https://github.com/gabormakrai/dijkstra-performance/blob/master/Implementation.md)
* [Data generation and measurement scenarios](https://github.com/gabormakrai/dijkstra-performance/blob/master/Data.md)
* [Results](https://github.com/gabormakrai/dijkstra-performance/blob/master/Results.md)

