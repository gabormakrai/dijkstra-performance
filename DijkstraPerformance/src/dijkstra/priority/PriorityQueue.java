package dijkstra.priority;

public interface PriorityQueue<E extends PriorityObject> {
	public void add(E item);
	public void decreasePriority(E item, double priority);
	public E extractMin();
	public void clear();
	public int size();
}
