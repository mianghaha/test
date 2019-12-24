package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


public class Test2 {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> input = sc.textFile("./src/main/java/spark/wordCount.txt");
		System.out.println("Input had " + input.count() + " line");
		for(String line : input.take(10)) {
			System.out.println(line);
		}
	}
}
