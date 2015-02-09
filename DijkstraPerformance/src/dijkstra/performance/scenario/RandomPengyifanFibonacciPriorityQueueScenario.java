package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.PengyifanDijkstraPriorityObject;
import dijkstra.priority.impl.PengyifanFibonacciPriorityQueue;

public class RandomPengyifanFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	PengyifanDijkstraPriorityObject[] priorityObjectArray;
	PengyifanFibonacciPriorityQueue priorityQueue;
	Random random;
	
	int size;
	int arcs;
	int previosArrayBuilds;
	
	public RandomPengyifanFibonacciPriorityQueueScenario(int size, int arcs, int previousArrayBuilds, Random random) {
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
		priorityQueue = new PengyifanFibonacciPriorityQueue();
		priorityObjectArray = new PengyifanDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new PengyifanDijkstraPriorityObject(i, 0.0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		Random random = new Random(randomSeed);
		
		previous = new int[size];
		generator.generateRandomGraph(size, arcs, random);
		priorityQueue = new PengyifanFibonacciPriorityQueue();
		priorityObjectArray = new PengyifanDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new PengyifanDijkstraPriorityObject(i, 0.0);
		}

		int origin = random.nextInt(size);
//		System.out.println("origin: " + origin);
		PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
