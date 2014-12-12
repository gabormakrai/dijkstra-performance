package dijkstra.priority.impl;

import com.growingwiththeweb.dataStructures.FibonacciHeap;

import dijkstra.priority.PriorityObject;

public class GrowingWithTheWebDijkstraPriorityObject extends PriorityObject {
	
	public FibonacciHeap.Node<PriorityObject> node;

	public GrowingWithTheWebDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
