package dijkstra.priority.impl;

import org.apache.nutch.util.FibonacciHeap;

import dijkstra.priority.DijkstraPriorityObject;

public class NutchFibonacciPriorityQueue implements dijkstra.priority.PriorityQueue<DijkstraPriorityObject> {
	
	FibonacciHeap heap = new FibonacciHeap();
	
	@Override
	public void add(DijkstraPriorityObject item) {
		heap.add(item, item.priority);
	}

	@Override
	public void decreasePriority(DijkstraPriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(item, priority);
	}

	@Override
	public DijkstraPriorityObject extractMin() {
		return (DijkstraPriorityObject) heap.popMin();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap(); 
	}

	@Override
	public int size() {
		return heap.size();
	}	

}
