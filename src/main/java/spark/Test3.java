package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;


public class Test3 {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> input = sc.textFile("./src/main/java/spark/wordCount.txt");
		JavaRDD<String> error = input.filter(
				new Function<String, Boolean>(){
					public Boolean call(String x) {
						return x.contains("error");
					}
				});
		
		error.saveAsTextFile("./src/main/java/spark/result2");
	}
}
