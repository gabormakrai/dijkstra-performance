package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.TeneightyDijkstraPriorityObject;
import dijkstra.priority.impl.TeneightyFibonacciPriorityQueue;

public class RandomTeneightyFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	TeneightyDijkstraPriorityObject[] priorityObjectArray;
	TeneightyFibonacciPriorityQueue priorityQueue;
	Random random;
	
	int size;
	double p;
	int previosArrayBuilds;
	
	public RandomTeneightyFibonacciPriorityQueueScenario(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previosArrayBuilds = previousArrayBuilds;
		this.random = random;
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
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new TeneightyFibonacciPriorityQueue();
		priorityObjectArray = new TeneightyDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new TeneightyDijkstraPriorityObject(i, 0.0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		Random random = new Random(randomSeed);
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new TeneightyFibonacciPriorityQueue();
		priorityObjectArray = new TeneightyDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new TeneightyDijkstraPriorityObject(i, 0.0);
		}
		int origin = random.nextInt(size);
//		System.out.println("origin: " + origin);
		PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		return previous;
	}
}
