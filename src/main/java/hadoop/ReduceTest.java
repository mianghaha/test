package hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceTest extends Reducer<Text, IntWritable, Text, IntWritable>{

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int maxValues = Integer.MIN_VALUE;
		for(IntWritable value : values) {
			System.out.println("thread=" + Thread.currentThread().getName() + ",maxValues=" + maxValues + ",value=" + value.get());
			maxValues = Math.max(maxValues, value.get());
		}
		System.out.println("final.maxValues=" + maxValues);
		context.write(key, new IntWritable(maxValues));
	}
}
