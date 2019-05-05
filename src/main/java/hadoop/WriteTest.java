package hadoop;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.StringUtils;

public class WriteTest {

	public static void main(String[] args) throws IOException {
		byte[] a = serialize(new VIntWritable(12));
		System.out.println("VIntWritable byteArrat=" + StringUtils.byteToHexString(a));
		a = serialize(new IntWritable(12));
		System.out.println("IntWritable byteArrat=" + StringUtils.byteToHexString(a));
	}
	
	public static byte[] serialize(Writable wirtable) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		wirtable.write(dataOut);
		dataOut.close();
		return out.toByteArray();
	}
}
