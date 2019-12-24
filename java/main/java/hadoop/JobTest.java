package hadoop;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

import utils.JsonUtil;

public class JobTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {			
			Job job = Job.getInstance();
			job.setJarByClass(JobTest.class);
			job.setJobName("test");
			
			String inputPath = "./src/main/java/haoop/input.txt";
			String outputPath = "./src/main/java/hadoopoutput.txt";
			
			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			
			job.setMapperClass(MapTest.class);
			job.setReducerClass(ReduceTest.class);
			job.setCombinerClass(ReduceTest.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			System.exit(job.waitForCompletion(true) ? 0 : 1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
