package dijkstra.base;

import java.util.HashSet;
import java.util.LinkedList;

public class BaseDijkstra {
	public static void createPreviousArray(int[][] neighbours, double[][] weights, int source, double[] distance, int[] previous) {
		
		int largestNodeId = neighbours.length + 1;
		
		for (int i = 0; i < largestNodeId + 1; ++i) {
			distance[i] = Double.MAX_VALUE;
			previous[i] = -1;
		}
		
		distance[source] = 0.0;
		
		HashSet<Integer> verticies = new HashSet<Integer>();
		for (int i = 0; i < largestNodeId + 1; ++i) {
			verticies.add(i);
		}
		
		while (verticies.size() != 0) {
			
			int u = -1;
			
			// search the element where the distance is minimum
			for (int v : verticies) {
				if (u == -1) {
					u = v;
				} else {
					if (distance[u] > distance[v]) {
						u = v;
					}
				}
			}
			
			verticies.remove(u);
			// find the neighbours
						
			for (int i = 0; i < neighbours[u].length; ++i) {
				double alt = distance[u] + weights[u][i];
				if (alt < distance[neighbours[u][i]]) {
					distance[neighbours[u][i]] = alt;
					previous[neighbours[u][i]] = u;
				}
			}
		}
	}
	
	public int[] shortestPath(int[] previous, int destination) {
		if (previous[destination] == -1) {
			return null;
		}
		
		LinkedList<Integer> reversedRoute = new LinkedList<>();
		int u = destination;
		
		while (u != -1) {
			reversedRoute.add(u);
			u = previous[u];
		}
		
		int[] path = new int[reversedRoute.size()];
		for (int i = 0; i < path.length; ++i) {
			path[i] = reversedRoute.get(path.length - 1 - i);
		}
		
		return path;
	}		
}
