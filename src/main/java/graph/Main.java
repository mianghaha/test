package graph;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class Main {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		Graph graph = Util.transGraph(Util.getSample());
		System.out.println(JsonUtil.TransToJson(Floyd.solution(graph)));
	}

}
