package dijkstra.performance;

public interface PerformanceScenario {
	public void generateGraph();
	public void runShortestPath();
	public int[] testPrevious(int randomSeed);
}
