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
	
	int randomSeed;
	int size;
	int arcs;
	int previosArrayBuilds;
	
	public RandomBaseScenario(int size, int arcs, int previousArrayBuilds, int randomSeed) {
		this.size = size;
		this.arcs = arcs;
		this.previosArrayBuilds = previousArrayBuilds;
		this.randomSeed = randomSeed;
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
		random = new Random(randomSeed);
		distance = new double[size];
		previous = new int[size];
		generator.generateRandomGraph(size, 0, random);
		random = new Random(randomSeed);
	}
}
