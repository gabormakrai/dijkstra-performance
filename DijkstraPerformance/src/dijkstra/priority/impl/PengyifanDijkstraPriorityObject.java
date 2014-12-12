package dijkstra.priority.impl;

import com.pengyifan.commons.collections.heap.FibonacciHeap;

import dijkstra.priority.DijkstraPriorityObject;

public class PengyifanDijkstraPriorityObject extends DijkstraPriorityObject {
	
	public FibonacciHeap.Entry entry;

	public PengyifanDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}
	
}
