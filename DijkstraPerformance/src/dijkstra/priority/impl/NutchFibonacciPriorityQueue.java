package dijkstra.priority.impl;

import org.apache.nutch.util.FibonacciHeap;

import dijkstra.priority.PriorityObject;

public class NutchFibonacciPriorityQueue implements dijkstra.priority.PriorityQueue<PriorityObject> {
	
	FibonacciHeap heap = new FibonacciHeap();
	
	@Override
	public void add(PriorityObject item) {
		heap.add(item, item.priority);
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(item, priority);
	}

	@Override
	public PriorityObject extractMin() {
		return (PriorityObject) heap.popMin();
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
