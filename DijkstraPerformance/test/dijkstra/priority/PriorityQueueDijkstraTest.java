package dijkstra.priority;

import static org.junit.Assert.*;

import org.junit.Test;

import dijkstra.priority.impl.TreeSetPriorityQueue;

public class PriorityQueueDijkstraTest {
	
	@Test
	public void test() {
		int[][] neighbours = new int[][] {
			new int[] { 1, 2 },
			new int[] { 3 },
			new int[] { 3},
			null
		};
			
		double[][] weights = new double[][] {
			new double[] { 1, 100},
			new double[] { 10 },
			new double[] { 10 },
			null
		};
			
		int[] previous = new int[4];
		
		PriorityObject[] array = new PriorityObject[4];
		for (int i = 0; i < 4; ++i) {
			array[i] = new PriorityObject(i, 1.0);
		}
		TreeSetPriorityQueue priorityQueue = new TreeSetPriorityQueue();
		
		PriorityQueueDijkstra.createPreviousArray(neighbours, weights, 0, previous, array, priorityQueue);
		int[] path = PriorityQueueDijkstra.shortestPath(previous, 3);
		
		assertTrue(path != null);
		assertEquals(3, path.length);
		assertEquals(0, path[0]);
		assertEquals(1, path[1]);
		assertEquals(3, path[2]);		
	}
	
}
