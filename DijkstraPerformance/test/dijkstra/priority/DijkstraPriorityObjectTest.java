package dijkstra.priority;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class DijkstraPriorityObjectTest {

	@Test
	public void test() {
		DijkstraPriorityObject[] array = new DijkstraPriorityObject[] {
			new DijkstraPriorityObject(0, 12.0),
			new DijkstraPriorityObject(1, 23.0),
			new DijkstraPriorityObject(2, 18.0)
		};
		
		Arrays.sort(array);
		
		assertTrue(array != null);
		assertEquals(3, array.length);
		assertEquals(0, array[0].node);
		assertEquals(2, array[1].node);
		assertEquals(1, array[2].node);
	}
}
