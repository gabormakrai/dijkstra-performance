package dijkstra.priority.impl;

import org.teneighty.heap.Heap.Entry;

import dijkstra.priority.PriorityObject;

public class TeneightyDijkstraPriorityObject extends PriorityObject {
	
	public Entry<Double, TeneightyDijkstraPriorityObject> entry;

	public TeneightyDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
