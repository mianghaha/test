package elk;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import utils.JsonUtil;

public class HighLevelClientTest {
	 private static RestHighLevelClient restHighLevelClient;
	 private static RestClient restClient;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestClientBuilder builder = RestClient.builder(new HttpHost("10.236.254.201", 9200));
		restClient = builder.build();
		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
	    sourceBuilder.size(10);
	    String[] includes = {"gameid", "rolelevel"};
	    sourceBuilder.fetchSource(includes, new String[0]);
	    
	    MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "费德勒");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("tag", "体育");
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
//        rangeQueryBuilder.gte("2018-01-26T08:00:00Z");
//        rangeQueryBuilder.lte("2018-01-26T20:00:00Z");
//	    BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		
	    SearchRequest searchRequest = new SearchRequest("task-keel-215-2017-10-31");
//	    searchRequest.types("task");
	    searchRequest.source(sourceBuilder);
	    try {
            SearchResponse response = restHighLevelClient.search(searchRequest);
            SearchHits hits = response.getHits();
            SearchHit[] hitArray = hits.getHits();
            Map<String, Object> fields = hitArray[1].getSourceAsMap();
            System.out.println(JsonUtil.TransToJson(fields));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}

}
