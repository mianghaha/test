package graph;

import java.util.HashMap;
import java.util.Map;

public class Node {

	Node(int id){
		this.id = id;
	}
	
	public int id;
	public Map<Node, Integer> distanceMap = new HashMap<Node, Integer>();
}
