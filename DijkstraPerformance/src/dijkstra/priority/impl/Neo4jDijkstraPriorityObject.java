package dijkstra.priority.impl;

import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import dijkstra.priority.DijkstraPriorityObject;

public class Neo4jDijkstraPriorityObject extends DijkstraPriorityObject {
	
	public FibonacciHeap<DijkstraPriorityObject>.FibonacciHeapNode node;

	public Neo4jDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
