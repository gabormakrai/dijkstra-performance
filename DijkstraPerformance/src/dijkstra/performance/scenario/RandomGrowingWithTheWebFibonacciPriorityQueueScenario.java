package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.GrowingWithTheWebDijkstraPriorityObject;
import dijkstra.priority.impl.GrowingWithTheWebFibonacciPriorityQueue;

public class RandomGrowingWithTheWebFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	GrowingWithTheWebDijkstraPriorityObject[] priorityObjectArray;
	GrowingWithTheWebFibonacciPriorityQueue priorityQueue;
	Random random;
	
	int size;
	int arcs;
	int previosArrayBuilds;
	
	public RandomGrowingWithTheWebFibonacciPriorityQueueScenario(int size, int arcs, int previousArrayBuilds, Random random) {
		this.size = size;
		this.arcs = arcs;
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
		generator.generateRandomGraph(size, arcs, random);
		priorityQueue = new GrowingWithTheWebFibonacciPriorityQueue();
		priorityObjectArray = new GrowingWithTheWebDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new GrowingWithTheWebDijkstraPriorityObject(i, 0.0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		
		previous = new int[size];
		generator.generateRandomGraph(size, arcs, random);
		priorityQueue = new GrowingWithTheWebFibonacciPriorityQueue();
		priorityObjectArray = new GrowingWithTheWebDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new GrowingWithTheWebDijkstraPriorityObject(i, 0.0);
		}
		
		int origin = random.nextInt(size);
//		System.out.println("origin: " + origin);
		PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
