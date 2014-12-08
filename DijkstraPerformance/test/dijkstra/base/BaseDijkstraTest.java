package dijkstra.base;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseDijkstraTest {

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
		double[] distance = new double[4];
		
		BaseDijkstra.createPreviousArray(neighbours, weights, 0, distance, previous);
		int[] path = BaseDijkstra.shortestPath(previous, 3);
		
		assertTrue(path != null);
		assertEquals(3, path.length);
		assertEquals(0, path[0]);
		assertEquals(1, path[1]);
		assertEquals(3, path[2]);
	}

}
