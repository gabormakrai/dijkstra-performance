package dijkstra.priority.impl;

import com.keithschwarz.FibonacciHeap.Entry;

import dijkstra.priority.PriorityObject;

public class KeithschwarzDijkstraPriorityObject extends PriorityObject {
	
	public Entry<KeithschwarzDijkstraPriorityObject> entry;

	public KeithschwarzDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
