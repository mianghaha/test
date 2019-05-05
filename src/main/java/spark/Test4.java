package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;


public class Test4 {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> input = sc.textFile("./src/main/java/spark/wordCount.txt");
		input.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String v1) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2(v1, 1);
			}
		}).re
		System.out.println("Input had " + input.count() + " line");
		for(String line : input.take(10)) {
			System.out.println(line);
		}
	}
}
