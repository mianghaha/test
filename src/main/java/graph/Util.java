package graph;

public class Util {

	public static int[][] getSample(){
		int[][] tu = {
				{0,1,5,-1,-1,-1,-1,-1,-1},
				{1,0,3,7,5,-1,-1,-1,-1},
				{5,3,0,-1,1,7,-1,-1,-1},
				{-1,7,-1,0,2,-1,3,-1,-1},
				{-1,5,1,2,0,3,6,9,-1},
				{-1,-1,7,-1,3,0,-1,5,-1},
				{-1,-1,-1,3,6,-1,0,2,7},
				{-1,-1,-1,-1,9,5,2,0,4},
				{-1,-1,-1,-1,-1,-1,7,4,0}};
		return tu;
	}
	
	public static Graph transGraph(int[][] tu) {
		Node[] nodes = new Node[tu.length];
		for(int i = 0; i < tu.length; i++) {
			Node nodeI = nodes[i];
			if(nodeI == null) {
				nodeI = new Node(i);
				nodes[i] = nodeI;
			}
			
			for(int j = 0; j < tu[i].length; j++) {
				if(tu[i][j] > 0) {
					Node nodeJ = nodes[j];
					if(nodeJ == null) {
						nodeJ = new Node(j);
						nodes[j] = nodeJ;
					}
					if(tu[i][j] > 0) {
						nodeI.distanceMap.put(nodeJ, tu[i][j]);
					}
				}
			}
		}
		return new Graph(nodes);
	}
}
