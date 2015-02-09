package dijkstra.priority.impl;

import com.keithschwarz.FibonacciHeap;
import com.keithschwarz.FibonacciHeap.Entry;

import dijkstra.priority.PriorityObject;

public class KeithschwarzFibonacciPriorityQueue implements dijkstra.priority.PriorityQueue<PriorityObject> {
	
	com.keithschwarz.FibonacciHeap<KeithschwarzDijkstraPriorityObject> heap = new FibonacciHeap<>();

	@Override
	public void add(PriorityObject item) {
		Entry<KeithschwarzDijkstraPriorityObject> entry = heap.enqueue((KeithschwarzDijkstraPriorityObject)item, item.priority);
		((KeithschwarzDijkstraPriorityObject)item).entry = entry;
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((KeithschwarzDijkstraPriorityObject)item).entry, priority);
	}

	@Override
	public PriorityObject extractMin() {
		return heap.dequeueMin().getValue();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap<>();
	}

	@Override
	public int size() {
		return heap.size();
	}	

}
