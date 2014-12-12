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
		
		PerformanceScenario scenarioBase = new RandomBaseScenario(1000, 100000, 20, 42);
		PerformanceScenario scenarioPriorityQueue = new RandomTreeSetPriorityQueueScenario(1000, 100000, 20, 42);
		PerformanceScenario scenarioNeo4jFibonacciPriorityQueue = new RandomNeo4jFibonacciPriorityQueueScenario(1000, 100000, 20, 42);
		PerformanceScenario scenarioNutchFibonacciPriorityQueue = new RandomNutchFibonacciPriorityQueueScenario(1000, 100000, 20, 42);
		PerformanceScenario scenarioTeneightyFibonacciPriorityQueue = new RandomTeneightyFibonacciPriorityQueueScenario(1000, 100000, 20, 42);
	
		PerformanceEngine engine = new PerformanceEngine(scenarioBase);
		engine.startMeasurement(20, false);
		
		PerformanceEngine engine2 = new PerformanceEngine(scenarioPriorityQueue);
		engine2.startMeasurement(20, false);
		
		PerformanceEngine engine3 = new PerformanceEngine(scenarioNeo4jFibonacciPriorityQueue);
		engine3.startMeasurement(20, false);
		
		PerformanceEngine engine4 = new PerformanceEngine(scenarioNutchFibonacciPriorityQueue);
		engine4.startMeasurement(20, false);
		
		PerformanceEngine engine5 = new PerformanceEngine(scenarioTeneightyFibonacciPriorityQueue);
		engine5.startMeasurement(20, false);

	}
}
