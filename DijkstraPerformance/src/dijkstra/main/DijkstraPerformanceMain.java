package dijkstra.main;

import java.util.Random;

import dijkstra.base.BaseDijkstra;
import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceEngine;
import dijkstra.performance.PerformanceScenario;

public class DijkstraPerformanceMain {
	public static void main(String[] args) {
		
		PerformanceScenario scenario = new PerformanceScenario() {
			
			int randomSeed = 42;
			NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
			int size = 1000;
			int previosArrayBuilds = 200;
			double[] distance;
			int[] previous;
			Random random;
			
			@Override
			public void runShortestPath() {
				for (int i = 0; i < previosArrayBuilds; ++i) {
					int origin = random.nextInt(size);
					BaseDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, distance, previous);
				}
			}
			
			@Override
			public void generateGraph() {
				random = new Random(randomSeed);
				distance = new double[size];
				previous = new int[size];
				generator.generateRandomGraph(size, 0, random);
			}
		};
		
		PerformanceEngine engine = new PerformanceEngine(scenario);
		engine.startMeasurement(100);
		
	}
}
