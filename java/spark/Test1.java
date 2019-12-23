package spark;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.Function2;

import scala.Tuple2;

public class Test1 {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> input = sc.textFile("./src/main/java/spark/wordCount.txt");
		JavaRDD<String> words = input.flatMap(
				new FlatMapFunction<String, String>(){
					public Iterator<String> call(String x){
						return Arrays.asList(x.split(" ")).iterator();
					}
				});
		
		JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer> (){
			public Tuple2<String, Integer> call(String x){
				return new Tuple2(x, 1);
			}
		}).reduceByKey(new Function2<Integer, Integer, Integer>(){
			public Integer call(Integer x, Integer y) {
				return x + y;
			}
		});
		
		counts.saveAsTextFile("./src/main/java/spark/result");
	}
}
