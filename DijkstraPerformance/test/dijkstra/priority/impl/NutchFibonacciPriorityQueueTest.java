package dijkstra.priority.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import dijkstra.priority.DijkstraPriorityObject;

public class NutchFibonacciPriorityQueueTest {
	
	@Test
	public void extractTest() {
		
		DijkstraPriorityObject[] array = new DijkstraPriorityObject[] {
			new DijkstraPriorityObject(2, 12.0),
			new DijkstraPriorityObject(1, 23.0),
			new DijkstraPriorityObject(0, 18.0)
		};		
		
		NutchFibonacciPriorityQueue priorityQueue = new NutchFibonacciPriorityQueue();
		priorityQueue.add(array[0]);
		priorityQueue.add(array[1]);
		priorityQueue.add(array[2]);
		
		DijkstraPriorityObject min = priorityQueue.extractMin();
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
		
		DijkstraPriorityObject[] array = new DijkstraPriorityObject[] {
			new DijkstraPriorityObject(2, 12.0),
			new DijkstraPriorityObject(1, 23.0),
			new DijkstraPriorityObject(0, 18.0)
		};		
		
		NutchFibonacciPriorityQueue priorityQueue = new NutchFibonacciPriorityQueue();
		priorityQueue.add(array[0]);
		priorityQueue.add(array[1]);
		priorityQueue.add(array[2]);
		
		priorityQueue.decreasePriority(array[1], 5.0);
		assertEquals(3, priorityQueue.size());
		
		DijkstraPriorityObject min = priorityQueue.extractMin();
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
