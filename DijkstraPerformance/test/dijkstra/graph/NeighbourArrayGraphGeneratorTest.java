package dijkstra.graph;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class NeighbourArrayGraphGeneratorTest {
	
	@Test
	public void test() {
		NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
		generator.generateRandomGraph(10, 50, new Random(42));
		assertTrue(generator.neighbours != null);
	}
	
}
