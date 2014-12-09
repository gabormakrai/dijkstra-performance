package dijkstra.priority.impl;

import java.util.TreeSet;

import dijkstra.priority.DijkstraPriorityObject;

public class TreeSetPriorityQueue implements dijkstra.priority.PriorityQueue<DijkstraPriorityObject> {
	
	TreeSet<DijkstraPriorityObject> tree = new TreeSet<DijkstraPriorityObject>();

	@Override
	public void add(DijkstraPriorityObject item) {
		tree.add(item);
	}

	@Override
	public void decreasePriority(DijkstraPriorityObject item, double priority) {
		tree.remove(item);
		item.priority = priority;
		tree.add(item);
	}

	@Override
	public DijkstraPriorityObject extractMin() {
		return tree.pollFirst();
	}

	@Override
	public void clear() {
		tree.clear();
	}

	@Override
	public int size() {
		return tree.size();
	}	

}
