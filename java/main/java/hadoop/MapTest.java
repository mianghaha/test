package hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.JsonUtil;

public class MapTest extends Mapper<LongWritable, Text, Text, IntWritable>{
	enum CounterName{
		NAME1,
		NAME2
	}

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
//		String sub = line.substring(4, 10);
		int num = Integer.valueOf(line.substring(0, 3));
		context.write(new Text("1"), new IntWritable(num));
		if(num % 2 == 0) {
			context.getCounter(CounterName.NAME2).increment(1);
		}else {
			context.getCounter(CounterName.NAME1).increment(1);
		}

		System.out.println("map:split" + JsonUtil.TransToJson(context.getInputSplit().getLocations()));
	}
}
