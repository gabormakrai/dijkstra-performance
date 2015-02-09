package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityObject;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.TreeSetPriorityQueue;

public class RandomTreeSetPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	PriorityObject[] priorityObjectArray;
	TreeSetPriorityQueue priorityQueue;
	Random random;
	
	int size;
	double p;
	int previosArrayBuilds;
	
	public RandomTreeSetPriorityQueueScenario(int size, double p, int previousArrayBuilds, Random random) {
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
		priorityQueue = new TreeSetPriorityQueue();
		priorityObjectArray = new PriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new PriorityObject(i, 0.0);
		}
	}
	
	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		Random random = new Random(randomSeed);
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new TreeSetPriorityQueue();
		priorityObjectArray = new PriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new PriorityObject(i, 0.0);
		}		
		int origin = random.nextInt(size);
//		System.out.println("origin: " + origin);
		PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		return previous;
	}
}
