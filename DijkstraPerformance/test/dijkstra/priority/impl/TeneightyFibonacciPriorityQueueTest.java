package dijkstra.priority.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dijkstra.priority.PriorityObject;

public class TeneightyFibonacciPriorityQueueTest {
	
	@Test
	public void extractTest() {
		
		TeneightyDijkstraPriorityObject[] array = new TeneightyDijkstraPriorityObject[] {
			new TeneightyDijkstraPriorityObject(2, 12.0),
			new TeneightyDijkstraPriorityObject(1, 23.0),
			new TeneightyDijkstraPriorityObject(0, 18.0)
		};		
		
		TeneightyFibonacciPriorityQueue priorityQueue = new TeneightyFibonacciPriorityQueue();
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
		
		TeneightyDijkstraPriorityObject[] array = new TeneightyDijkstraPriorityObject[] {
			new TeneightyDijkstraPriorityObject(2, 12.0),
			new TeneightyDijkstraPriorityObject(1, 23.0),
			new TeneightyDijkstraPriorityObject(0, 18.0)
		};		
		
		TeneightyFibonacciPriorityQueue priorityQueue = new TeneightyFibonacciPriorityQueue();
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
