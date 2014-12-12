package dijkstra.priority.impl;

import com.pengyifan.commons.collections.heap.FibonacciHeap;

import dijkstra.priority.PriorityObject;

public class PengyifanDijkstraPriorityObject extends PriorityObject {
	
	public FibonacciHeap.Entry entry;

	public PengyifanDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}
	
}
