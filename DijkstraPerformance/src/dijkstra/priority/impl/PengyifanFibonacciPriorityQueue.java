package dijkstra.priority.impl;

import com.pengyifan.commons.collections.heap.FibonacciHeap;
import com.pengyifan.commons.collections.heap.FibonacciHeap.Entry;

import dijkstra.priority.DijkstraPriorityObject;

public class PengyifanFibonacciPriorityQueue implements dijkstra.priority.PriorityQueue<DijkstraPriorityObject> {

	FibonacciHeap heap = new FibonacciHeap();
	int heapSize = 0;

	@Override
	public void add(DijkstraPriorityObject item) {
		Entry entry = new Entry(item.priority, item);
		heap.insert(entry);
		((PengyifanDijkstraPriorityObject)item).entry = entry;
		++heapSize;
	}

	@Override
	public void decreasePriority(DijkstraPriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((PengyifanDijkstraPriorityObject)item).entry, priority);
	}

	@Override
	public DijkstraPriorityObject extractMin() {
		if (heapSize > 0) {
			--heapSize;
		} else {
			return null;
		}
		return (DijkstraPriorityObject) heap.extractMin().getObject();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap();
		heapSize = 0;
	}

	@Override
	public int size() {
		return heapSize;
		
	}	

}
