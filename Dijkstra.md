All the different versions of Dijkstra's algorithm differs in the way they are creating the previous vector. The first naive implementation's pseudo code is the following:

```
function Dijkstra(Graph, source):
  dist[source]  := 0                  // Distance from source to source
  prev[source]  := undefined          // Previous node in optimal path initialization

  for each vertex v in Graph:         // Initializations
    if v ≠ source                     // Where v has not yet been removed from Q (unvisited nodes)
      dist[v]  := infinity            // Unknown distance function from source to v
      prev[v]  := undefined           // Previous node in optimal path from source
    end if 
    add v to Q                        // All nodes initially in Q (unvisited nodes)
  end for

  while Q is not empty:               // The main loop
    u := vertex in Q with min dist[u] // Source node in first case
    remove u from Q 

    for each neighbor v of u:         // where v has not yet been removed from Q.
      alt := dist[u] + length(u, v)
      if alt < dist[v]:               // A shorter path to v has been found
        dist[v]  := alt 
        prev[v]  := u 
      end if
    end for
  end while

  return dist[], prev[]

end function
```

This is going to be my first, native implementation of the Dijkstra's algorithm where I am using a simple full scan search for finding the the closest vertex in the distance vector. This comes with O(n) time complexity so the overall time complexity is O(|E| + |V|<sup>2</sup>). It is clear that finding the closest vertex/node can be achieved by maintaining an advanced data structure to reduce the search time. Prioirty Queues can help in this, where the queue can be implemented as a binary heap reducing time complexity to O(|E| + |V|log<sup>2</sup>|V|) or a more advanced data structure developed specially for this algorithm, the Fibonacci heap where the time complexity can be reduced to O(|E| + |V|log|V|). The code for calculating the previous vector in general is the following:

```
function Dijkstra(Graph, source):
  dist[source] := 0                 // Initializations
  for each vertex v in Graph:           
    if v ≠ source
      dist[v] := infinity           // Unknown distance from source to v
      previous[v] := undefined      // Predecessor of v
    end if
    Q.add_with_priority(v,dist[v])
  end for 

  while Q is not empty:             // The main loop
    u := Q.extract_min()            // Remove and return best vertex
    mark u as scanned
    
    for each neighbor v of u:
      if v is not yet scanned:
        alt = dist[u] + length(u, v) 
        if alt < dist[v]
          dist[v] := alt
          previous[v] := u
          Q.decrease_priority(v,alt)
        end if
      end if
    end for
  end while

  return previous[]
end function
```


