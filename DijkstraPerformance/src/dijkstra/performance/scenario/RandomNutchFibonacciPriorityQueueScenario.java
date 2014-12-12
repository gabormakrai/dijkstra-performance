package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityObject;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.NutchFibonacciPriorityQueue;

public class RandomNutchFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	PriorityObject[] priorityObjectArray;
	NutchFibonacciPriorityQueue priorityQueue;
	Random random;
	
	int randomSeed;
	int size;
	int arcs;
	int previosArrayBuilds;
	
	public RandomNutchFibonacciPriorityQueueScenario(int size, int arcs, int previousArrayBuilds, int randomSeed) {
		this.size = size;
		this.arcs = arcs;
		this.previosArrayBuilds = previousArrayBuilds;
		this.randomSeed = randomSeed;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previosArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		}
	}
	
	@Override
	public void generateGraph() {
		random = new Random(randomSeed);
		previous = new int[size];
		generator.generateRandomGraph(size, arcs, random);
		random = new Random(randomSeed);
		priorityQueue = new NutchFibonacciPriorityQueue();
		priorityObjectArray = new PriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new PriorityObject(i, 0.0);
		}
	}
}
