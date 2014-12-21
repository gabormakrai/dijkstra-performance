package dijkstra.main;

import dijkstra.performance.PerformanceEngine;
import dijkstra.performance.PerformanceScenario;
import dijkstra.performance.scenario.RandomBaseScenario;
import dijkstra.performance.scenario.RandomNeo4jFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomNutchFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomTeneightyFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomTreeSetPriorityQueueScenario;

public class DijkstraPerformanceMain {
	public static void main(String[] args) {
		new DijkstraPerformanceMain().run();
	}
	
	private int calculateArcNumber(int size, double p) {
		return Math.max((int)Math.round(size * size * p) - (size - 1) * 2, (size - 1) * 2);
	}
	
	private void run() {
		double[][] results = new double[199][];
		for (int i = 0; i < 199; ++i) {
			results[i] = parameterizedMeasurement(10 + 10 * i, 0.1);
		}
		for (int i = 0; i < 199; ++i) {
			if (results[i] == null) {
				continue;
			}
			for (int j = 0; j < results[i].length; ++j) {
				System.out.print(results[i][j]);
				System.out.print(",");
			}
			System.out.println();
		}
	}
	
	private double[] parameterizedMeasurement(int size, double p) {
		
		System.out.println("Size: " + size + ", p: " + p + ", #arcs: " + calculateArcNumber(size, p));
		
		PerformanceScenario scenarioBase = new RandomBaseScenario(size, p, 20, 42);
		PerformanceScenario scenarioPriorityQueue = new RandomTreeSetPriorityQueueScenario(size, p, 20, 42);
		PerformanceScenario scenarioNeo4jFibonacciPriorityQueue = new RandomNeo4jFibonacciPriorityQueueScenario(size, p, 20, 42);
		PerformanceScenario scenarioNutchFibonacciPriorityQueue = new RandomNutchFibonacciPriorityQueueScenario(size, p, 20, 42);
		PerformanceScenario scenarioTeneightyFibonacciPriorityQueue = new RandomTeneightyFibonacciPriorityQueueScenario(size, p, 20, 42);
	
		PerformanceEngine engine = new PerformanceEngine(scenarioBase);
		double m0 = engine.measurement(20, true, false, 3, 3);
		
		PerformanceEngine engine2 = new PerformanceEngine(scenarioPriorityQueue);
		double m1 = engine2.measurement(20, true, false, 3, 3);
		
		PerformanceEngine engine3 = new PerformanceEngine(scenarioNeo4jFibonacciPriorityQueue);
		double m2 = engine3.measurement(20, true, false, 3, 3);
		
		PerformanceEngine engine4 = new PerformanceEngine(scenarioNutchFibonacciPriorityQueue);
		double m3 = engine4.measurement(20, true, false, 3, 3);
		
		PerformanceEngine engine5 = new PerformanceEngine(scenarioTeneightyFibonacciPriorityQueue);
		double m4 = engine5.measurement(20, true, false, 3, 3);
		
		return new double[] { size, p, calculateArcNumber(size, p), m0, m1, m2, m3, m4 };
	}
}
