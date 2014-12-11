package dijkstra.priority.impl;

import org.teneighty.heap.FibonacciHeap;
import org.teneighty.heap.Heap.Entry;

import dijkstra.priority.DijkstraPriorityObject;

public class TeneightyFibonacciPriorityQueue implements dijkstra.priority.PriorityQueue<DijkstraPriorityObject> {

	FibonacciHeap<Double, TeneightyDijkstraPriorityObject> heap = new FibonacciHeap<>();

	@Override
	public void add(DijkstraPriorityObject item) {
		Entry<Double, TeneightyDijkstraPriorityObject> entry = heap.insert(item.priority, (TeneightyDijkstraPriorityObject)item);
		((TeneightyDijkstraPriorityObject)item).entry = entry;
	}

	@Override
	public void decreasePriority(DijkstraPriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((TeneightyDijkstraPriorityObject)item).entry, priority);
	}

	@Override
	public DijkstraPriorityObject extractMin() {
		return heap.extractMinimum().getValue();
	}

	@Override
	public void clear() {
		heap.clear();
	}

	@Override
	public int size() {
		return heap.getSize();
	}	

}
