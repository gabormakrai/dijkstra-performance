package dijkstra.priority;

public class DijkstraPriorityObject extends PriorityObject {
	public int node;
	public DijkstraPriorityObject(int node, double distance) {
		this.priority = distance;
		this.node = node;
	}
	@Override
	public String toString() {
		return "DijkstraPO(node:" + node + ",distance:" + priority + ")"; 
	}
}
