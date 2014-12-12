package dijkstra.priority.impl;

import java.util.Comparator;

import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import dijkstra.priority.PriorityObject;

public class Neo4jFibonacciPrioityQueue implements dijkstra.priority.PriorityQueue<PriorityObject> {
	
	Comparator<PriorityObject> priorityObjectComparator = new Comparator<PriorityObject>() {
		@Override
		public int compare(PriorityObject o1, PriorityObject o2) {
			return o1.compareTo(o2);
		}
	};
	
	FibonacciHeap<PriorityObject> heap = new FibonacciHeap<PriorityObject>(priorityObjectComparator);

	@Override
	public void add(PriorityObject item) {
		FibonacciHeap<PriorityObject>.FibonacciHeapNode node = heap.insert(item);
		((Neo4jDijkstraPriorityObject)item).node = node;
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((Neo4jDijkstraPriorityObject)item).node, item);
	}

	@Override
	public PriorityObject extractMin() {
		return heap.extractMin();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap<PriorityObject>(priorityObjectComparator);
	}

	@Override
	public int size() {
		return heap.size();
	}	
}
