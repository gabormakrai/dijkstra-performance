package dijkstra.priority.impl;

import java.util.Comparator;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import dijkstra.priority.DijkstraPriorityObject;

public class Neo4jFibonacciPrioityQueue implements dijkstra.priority.PriorityQueue<DijkstraPriorityObject> {
	
	Comparator<DijkstraPriorityObject> dijkstraPriorityObjectComparator = new Comparator<DijkstraPriorityObject>() {
		@Override
		public int compare(DijkstraPriorityObject o1, DijkstraPriorityObject o2) {
			return o1.compareTo(o2);
		}
	};
	
	FibonacciHeap<DijkstraPriorityObject> heap = new FibonacciHeap<DijkstraPriorityObject>(dijkstraPriorityObjectComparator);

	@Override
	public void add(DijkstraPriorityObject item) {
		FibonacciHeap<DijkstraPriorityObject>.FibonacciHeapNode node = heap.insert(item);
		((Neo4jDijkstraPriorityObject)item).node = node;
	}

	@Override
	public void decreasePriority(DijkstraPriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((Neo4jDijkstraPriorityObject)item).node, item);
	}

	@Override
	public DijkstraPriorityObject extractMin() {
		return heap.extractMin();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap<DijkstraPriorityObject>(dijkstraPriorityObjectComparator);
	}

	@Override
	public int size() {
		return heap.size();
	}	
}
