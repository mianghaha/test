package hadoop;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;

public class MRUtilTest {
	
	@Test
	public void processValidRecord() throws IOException {
		Text value = new Text("11123512312313399910101293819101023");
		new MapDriver<LongWritable, Text, Text, IntWritable>()
		.withMapper(new MapTest())
		.withInput(new LongWritable(1), value)
		.runTest();
	}
	
	@Test
	public void reduceTest() throws IOException {
		new ReduceDriver<Text, IntWritable, Text, IntWritable>()
		.withReducer(new ReduceTest())
		.withInput(new Text("1950"), Arrays.asList(new IntWritable(10), new IntWritable(5)))
		.withOutput(new Text("1950"), new IntWritable(10))
		.runTest();
	}

}
