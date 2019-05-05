package hadoop;

import java.io.IOException;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class WholeFileReader extends RecordReader<NullWritable, ByteWritable>{
	
	private boolean processed;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteWritable getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
