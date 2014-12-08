package dijkstra.graph;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class NeighbourArrayGraphGeneratorTest {
	
	@Test
	public void test() {
		NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
		generator.generateRandomGraph(10, 0, new Random(42));
		
		int arcs = 0;
		for (int i = 0; i < generator.neighbours.length; ++i) {
			if (generator.neighbours[i] == null) {
				continue;
			}
			arcs += generator.neighbours[i].length;
		}
		
		assertTrue(generator.neighbours != null);
		assertEquals(18, arcs);
	}
	
}
