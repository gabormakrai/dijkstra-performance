package dijkstra.main;

import java.util.Random;

import dijkstra.performance.PerformanceEngine;
import dijkstra.performance.PerformanceScenario;
import dijkstra.performance.scenario.RandomBaseScenario;
import dijkstra.performance.scenario.RandomGrowingWithTheWebFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomKeithschwarzFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomNeo4jFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomNutchFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomPengyifanFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomTeneightyFibonacciPriorityQueueScenario;
import dijkstra.performance.scenario.RandomTreeSetPriorityQueueScenario;

public class DijkstraPerformanceBase {
		
	protected int calculateArcNumber(int size, double p) {
		return Math.max((int)Math.round(size * size * p) - (size - 1) * 2, (size - 1) * 2);
	}
		
	protected double[] parameterizedMeasurement(int size, double p) {
		
		System.out.println("Size: " + size + ", p: " + p + ", #arcs: " + calculateArcNumber(size, p));
		
		PerformanceScenario scenarioBase = new RandomBaseScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioPriorityQueue = new RandomTreeSetPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioNeo4jFibonacciPriorityQueue = new RandomNeo4jFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioNutchFibonacciPriorityQueue = new RandomNutchFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioTeneightyFibonacciPriorityQueue = new RandomTeneightyFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioGrowingWithTheWebFibonacciPriorityQueue = new RandomGrowingWithTheWebFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioPengyifanFibonacciPriorityQueue = new RandomPengyifanFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
		PerformanceScenario scenarioKeithschwarzFibonacciPriorityQueue = new RandomKeithschwarzFibonacciPriorityQueueScenario(size, p, 20, new Random(42));
	
		int[] p0 = scenarioBase.testPrevious(42);
		PerformanceEngine engine0 = new PerformanceEngine(scenarioBase);
		double m0 = engine0.measurement(20, true, false, 3, 3);
		
		int[] p1 = scenarioPriorityQueue.testPrevious(42);
		PerformanceEngine engine1 = new PerformanceEngine(scenarioPriorityQueue);
		double m1 = engine1.measurement(20, true, false, 3, 3);
		
		int[] p2 = scenarioNeo4jFibonacciPriorityQueue.testPrevious(42);
		PerformanceEngine engine2 = new PerformanceEngine(scenarioNeo4jFibonacciPriorityQueue);
		double m2 = engine2.measurement(20, true, false, 3, 3);
		
		int[] p3 = scenarioNutchFibonacciPriorityQueue.testPrevious(42);
		PerformanceEngine engine3 = new PerformanceEngine(scenarioNutchFibonacciPriorityQueue);
		double m3 = engine3.measurement(20, true, false, 3, 3);
		
		int[] p4 = scenarioTeneightyFibonacciPriorityQueue.testPrevious(42);
		PerformanceEngine engine4 = new PerformanceEngine(scenarioTeneightyFibonacciPriorityQueue);
		double m4 = engine4.measurement(20, true, false, 3, 3);
		
//		int[] p5 = scenarioGrowingWithTheWebFibonacciPriorityQueue.testPrevious(42);
//		PerformanceEngine engine5 = new PerformanceEngine(scenarioGrowingWithTheWebFibonacciPriorityQueue);
//		double m5 = engine5.measurement(20, true, false, 3, 3);
		
//		int[] p6 = scenarioPengyifanFibonacciPriorityQueue.testPrevious(42);
//		PerformanceEngine engine6 = new PerformanceEngine(scenarioPengyifanFibonacciPriorityQueue);
//		double m6 = engine6.measurement(20, true, false, 3, 3);
		
		int[] p7 = scenarioKeithschwarzFibonacciPriorityQueue.testPrevious(42);
		PerformanceEngine engine7 = new PerformanceEngine(scenarioKeithschwarzFibonacciPriorityQueue);
		double m7 = engine7.measurement(20, true, false, 3, 3);
		
		// check previous arrays from the test runs
		for (int i = 0; i < p0.length; ++i) {
			if (p0[i] != p1[i] || p0[i] != p2[i] || p0[i] != p3[i] || p0[i] != p4[i]/* || p0[i] != p5[i]*/ || p0[i] != p7[i]) {
				throw new RuntimeException("Problem...");
			}
		}
		
		return new double[] { size, p, calculateArcNumber(size, p), m0, m1, m2, m3, m4/*, m5*/, m7 };
	}
}
