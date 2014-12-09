package dijkstra.priority;

public class PriorityObject implements Comparable<PriorityObject> {
	
	public double priority = 0.0;

	@Override
	public int compareTo(PriorityObject o) {
		if (priority < o.priority) {
			return -1;
		} else if (priority > o.priority) {
			return +1;
		} else {
			return 0;
		}
	}
}
