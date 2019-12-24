package graph;

import java.util.Map.Entry;

public class Floyd {

	public static int[][] solution(Graph graph) {
		int len = graph.nodeList.length;
		int[][] res = new int[len][len];
		for(int i = 0; i < len; i++) {
			Node sourceNode = graph.nodeList[i];
			for(Entry<Node, Integer> entry: sourceNode.distanceMap.entrySet()) {
				Node destinationNode = entry.getKey();
				res[sourceNode.id][destinationNode.id] = entry.getValue();
			}
		}
		
		for(int k = 0; k < len; k++) {
			for(int j = 0; j < len; j++) {
				for(int i = 0; i < len; i++) {
 					if(i == j || i == k || j == k || res[i][k] == 0 || res[k][j] == 0)continue;
					int cost = res[i][k] + res[k][j];
					if(res[i][j] == 0 || res[i][j] > cost) {
						res[i][j] = cost;
					}
				}
			}
		}
		return res;
	}
}
