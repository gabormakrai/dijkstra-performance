package dijkstra.performance;

public class PerformanceEngine {
	
	private final PerformanceScenario scenario;
	
	public long[] startTimes;
	public long[] graphGenerationTimes;
	public long[] endTimes;
	
	public PerformanceEngine(PerformanceScenario scenario) {
		this.scenario = scenario;
	}
	
	public void startMeasurement(int repeats, boolean printOutInnerResults) {
		startTimes = new long[repeats];
		graphGenerationTimes = new long[repeats];
		endTimes = new long[repeats];
		
		for (int i = 0; i < repeats; ++i) {
			startTimes[i] = System.nanoTime();
			scenario.generateGraph();
			graphGenerationTimes[i] = System.nanoTime();
			scenario.runShortestPath();
			endTimes[i] = System.nanoTime();
		}
		
		double averageShortestPathTime = 0;
		for (int i = 0; i < repeats; ++i) {
			averageShortestPathTime += (endTimes[i] - graphGenerationTimes[i])/1000000.0;
			if (printOutInnerResults) {
				System.out.println("" + i + ". run: " + startTimes[i] + "," + graphGenerationTimes[i] + "," + endTimes[i] + "->" + (endTimes[i] - graphGenerationTimes[i])/1000000.0);
			}
		}
		averageShortestPathTime /= (double)repeats;
		System.out.println("AverageShortestPathTime:" + averageShortestPathTime);
	}
}
