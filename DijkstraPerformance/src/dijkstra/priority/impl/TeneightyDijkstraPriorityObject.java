package dijkstra.priority.impl;

import org.teneighty.heap.Heap.Entry;

import dijkstra.priority.DijkstraPriorityObject;

public class TeneightyDijkstraPriorityObject extends DijkstraPriorityObject {
	
	public Entry<Double, TeneightyDijkstraPriorityObject> entry;

	public TeneightyDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
