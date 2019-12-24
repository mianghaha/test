package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ToolTest extends Configured implements Tool{
	
	@Override
	public int run(String[] args) throws Exception {

		Configuration conf = getConf();
		conf.addResource(new Path("src/main/java/hadoop/hadoop-local.xml"));
		Job job = Job.getInstance(conf, "MAX");
		job.setJarByClass(getClass());
		
		String inputPath = "src/main/java/hadoop/input.txt";
		String outputPath = "src/main/java/hadoop/output";

		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		
		job.setMapperClass(MapTest.class);
		job.setReducerClass(ReduceTest.class);
//		job.setCombinerClass(ReduceTest.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int code = ToolRunner.run(new ToolTest(), args);
		System.exit(code);
	}

}
