package dijkstra.priority;

public class PriorityObject implements Comparable<PriorityObject> {
	
	public double priority;
	public int node;
	
	public PriorityObject(int node, double priority) {
		this.node = node;
		this.priority = priority;
	}

	@Override
	public int compareTo(PriorityObject o) {
		if (priority > o.priority) {
			return +1;
		} else {
			return -1;
		}
	}
}
