package dijkstra.main;

import java.util.Random;

import dijkstra.graph.NeighbourArrayGraphGenerator;

public class GraphGeneratorDotOutputMain {
	
	// http://graphviz-dev.appspot.com/
	
	public static void main(String[] args) {
		createDotScript(10, 0.1);
		createDotScript(10, 0.3);
		createDotScript(10, 0.5);
		createDotScript(10, 0.9);
	}
	
	public static void createDotScript(int n, double p) {
		System.out.print("digraph graphname {");
		NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
		generator.generateRandomGraph(n, p, new Random(42));
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
		System.out.println("nodes: " + n);
		System.out.println("p: " + p);
		System.out.println("arcs: " + arcs);
	}
}
