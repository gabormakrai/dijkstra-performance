package dijkstra.graph;

import java.util.HashSet;
import java.util.Random;

public class NeighbourArrayGraphGenerator {
	
	public int[][] neighbours;
	public double[][] weights;
	
	public void generateRandomGraph(int size, double p, Random random) {
		
		HashSet<Integer>[] neighboursList = generateList(size);

		// create random spanning tree
		generateSpanningTree(neighboursList, random);
		
		// add more random arcs
		int arcs = (int)Math.round(size * size * p) - (size - 1) * 2; 
		addRandomArcs(arcs, neighboursList, random);

		// create neighbours array
		neighbours = createNeighboursArrat(neighboursList);
		
		// fill weights with random values
		weights = createWeightsArray(neighbours, random);
	}
	
	@SuppressWarnings("unchecked")
	private HashSet<Integer>[] generateList(int size) {
		HashSet<Integer>[] neighboursList = new HashSet[size];
		for (int i = 0; i < size; ++i) {
			neighboursList[i] = new HashSet<Integer>();
		}
		return neighboursList;
	}
	
	private void generateSpanningTree(HashSet<Integer>[] neighboursList, Random random) {
		boolean[] nodes = new boolean[neighboursList.length];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = false;
		}
		int nodeNumber = nodes.length;
		boolean firstIteration = true;
		while(nodeNumber != 0) {
			int v = random.nextInt(nodes.length);
			int w = random.nextInt(nodes.length);
			if (v == w) {
				continue;
			}
			if (firstIteration) {
				firstIteration = false;
			} else if ((nodes[v] && nodes[w]) || (!nodes[v] && !nodes[w])) {
				continue;
			}
			neighboursList[v].add(w);
			neighboursList[w].add(v);
			if (!nodes[v]) {
				nodes[v] = true;
				--nodeNumber;
			}
			if (!nodes[w]) {
				nodes[w] = true;
				--nodeNumber;
			}
		}
	}
	
	private int[][] createNeighboursArrat(HashSet<Integer>[] neighboursList) {
		int[][] neighbours = new int[neighboursList.length][];
		for (int i = 0; i < neighbours.length; ++i) {
			neighbours[i] = new int[neighboursList[i].size()];
			int j = 0;
			for (Integer neighbour : neighboursList[i]) {
				neighbours[i][j] = neighbour;
				++j;
			}
		}
		return neighbours;
	}
	
	private double[][] createWeightsArray(int[][] neighbours, Random random) {
		double[][] weights = new double[neighbours.length][];
		for (int i = 0; i < weights.length; ++i) {
			if (neighbours[i] == null) {
				continue;
			}
			weights[i] = new double[neighbours[i].length];
			for (int j = 0; j < neighbours[i].length; ++j) {
				weights[i][j] = random.nextDouble();
			}
		}
		return weights;
	}
	
	private void addRandomArcs(int arcs, HashSet<Integer>[] neighboursList, Random random) {
		int size = neighboursList.length;
		for (int i = 0; i < arcs; ++i) {
			int v = random.nextInt(size);
			int w = random.nextInt(size);
			
			if (neighboursList[v].contains(w) || v == w) {
				--i;
			}  else {
				neighboursList[v].add(w);
			}
		}
	}
	
}
