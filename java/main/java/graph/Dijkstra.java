package graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import utils.JsonUtil;

import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Dijkstra {

	public static Map<Integer, Integer> solution(Graph graph, int target) {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		Set<Node> finishSet = new HashSet<>();
		LinkedList<Node> unFinishedStack = new LinkedList<>();
		
		unFinishedStack.add(graph.nodeList[target]);
		while(!unFinishedStack.isEmpty()) {
			Node node = unFinishedStack.pop();
			finishSet.add(node);
			int nodeDistance = result.getOrDefault(node.id, 0);
			for(Entry<Node, Integer> entry : node.distanceMap.entrySet()) {
				Node destination = entry.getKey();
				int id = destination.id;
				int minDistance = result.getOrDefault(id, 0);
				int cost = entry.getValue();
				if(minDistance == 0 || minDistance > nodeDistance + cost) {
					result.put(id, nodeDistance + cost);
				}
				if(!finishSet.contains(destination)) {
					unFinishedStack.add(destination);
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Graph graph = Util.transGraph(Util.getSample());
		System.out.println(JsonUtil.TransToJson(solution(graph, 0)));
	}
}
