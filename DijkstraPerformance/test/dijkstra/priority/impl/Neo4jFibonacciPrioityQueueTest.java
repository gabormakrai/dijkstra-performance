package dijkstra.priority.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dijkstra.priority.PriorityObject;

public class Neo4jFibonacciPrioityQueueTest {
	@Test
	public void extractTest() {
		
		Neo4jDijkstraPriorityObject[] array = new Neo4jDijkstraPriorityObject[] {
			new Neo4jDijkstraPriorityObject(2, 12.0),
			new Neo4jDijkstraPriorityObject(1, 23.0),
			new Neo4jDijkstraPriorityObject(0, 18.0)
		};		
		
		Neo4jFibonacciPrioityQueue priorityQueue = new Neo4jFibonacciPrioityQueue();
		priorityQueue.add(array[0]);
		priorityQueue.add(array[1]);
		priorityQueue.add(array[2]);
		
		PriorityObject min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(2, min.node);
		assertEquals(2, priorityQueue.size());
		
		min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(0, min.node);
		assertEquals(1, priorityQueue.size());
		
		min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(1, min.node);
		assertEquals(0, priorityQueue.size());

		min = priorityQueue.extractMin();
		assertTrue(min == null);
	}
	
	@Test
	public void decreaseTest() {
		
		Neo4jDijkstraPriorityObject[] array = new Neo4jDijkstraPriorityObject[] {
			new Neo4jDijkstraPriorityObject(2, 12.0),
			new Neo4jDijkstraPriorityObject(1, 23.0),
			new Neo4jDijkstraPriorityObject(0, 18.0)
		};		
		
		Neo4jFibonacciPrioityQueue priorityQueue = new Neo4jFibonacciPrioityQueue();
		priorityQueue.add(array[0]);
		priorityQueue.add(array[1]);
		priorityQueue.add(array[2]);
		
		priorityQueue.decreasePriority(array[1], 5.0);
		assertEquals(3, priorityQueue.size());
		
		PriorityObject min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(1, min.node);
		assertEquals(2, priorityQueue.size());
		
		min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(2, min.node);
		assertEquals(1, priorityQueue.size());
		
		min = priorityQueue.extractMin();
		assertTrue(min != null);
		assertEquals(0, min.node);
		assertEquals(0, priorityQueue.size());

		min = priorityQueue.extractMin();
		assertTrue(min == null);
	}	

}
