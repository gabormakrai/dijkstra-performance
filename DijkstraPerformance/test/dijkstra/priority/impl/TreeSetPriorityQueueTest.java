package dijkstra.priority.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import dijkstra.priority.PriorityObject;

public class TreeSetPriorityQueueTest {
	
	@Test
	public void extractTest() {
		
		PriorityObject[] array = new PriorityObject[] {
			new PriorityObject(2, 12.0),
			new PriorityObject(1, 23.0),
			new PriorityObject(0, 18.0)
		};		
		
		TreeSetPriorityQueue priorityQueue = new TreeSetPriorityQueue();
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
		
		PriorityObject[] array = new PriorityObject[] {
			new PriorityObject(2, 12.0),
			new PriorityObject(1, 23.0),
			new PriorityObject(0, 18.0)
		};		
		
		TreeSetPriorityQueue priorityQueue = new TreeSetPriorityQueue();
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
