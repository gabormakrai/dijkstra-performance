package dijkstra.performance.scenario;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;
import dijkstra.performance.PerformanceScenario;
import dijkstra.priority.PriorityQueueDijkstra;
import dijkstra.priority.impl.Neo4jDijkstraPriorityObject;
import dijkstra.priority.impl.Neo4jFibonacciPrioityQueue;

public class RandomNeo4jFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	Neo4jDijkstraPriorityObject[] priorityObjectArray;
	Neo4jFibonacciPrioityQueue priorityQueue;
	Random random;
	
	int randomSeed;
	int size;
	double p;
	int previosArrayBuilds;
	
	public RandomNeo4jFibonacciPriorityQueueScenario(int size, double p, int previousArrayBuilds, int randomSeed) {
		this.size = size;
		this.p = p;
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
		generator.generateRandomGraph(size, p, random);
		random = new Random(randomSeed);
		priorityQueue = new Neo4jFibonacciPrioityQueue();
		priorityObjectArray = new Neo4jDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4jDijkstraPriorityObject(i, 0.0);
		}
	}
}
