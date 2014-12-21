package dijkstra.main;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;

public class GraphGeneratorDotOutputMain {
	public static void main(String[] args) {
		System.out.print("digraph graphname {");
		NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
		generator.generateRandomGraph(10, 0.1, new Random(42));
		int arcs = 0;
		for (int i = 0; i < generator.neighbours.length; ++i) {
			if (generator.neighbours[i] == null) {
				continue;
			}
			for (int j = 0; j < generator.neighbours[i].length; ++j) {
				System.out.print("" + i + " -> " + generator.neighbours[i][j] + "; ");
				++arcs;
			}
		}
		System.out.println("}");
		System.out.println("arcs: " + arcs);
	}
}
