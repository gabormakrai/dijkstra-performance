package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.base.BaseDijkstra;
import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;

public class RandomBaseScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	double[] distance;
	int[] previous;
	Random random;
	
	int size;
	double p;
	int previosArrayBuilds;
	
	public RandomBaseScenario(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previosArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previosArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			BaseDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, distance, previous);
		}
	}
	
	@Override
	public void generateGraph() {
		distance = new double[size];
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		Random random = new Random(randomSeed);
		generator.generateRandomGraph(size, p, random);
		int origin = random.nextInt(size);
		distance = new double[size];
		previous = new int[size];
//		System.out.println("origin: " + origin);
		BaseDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, distance, previous);
		return previous;
	}
}
