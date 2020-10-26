package math;

public class longtest {

	public static void main(String[] args) {		
		long long1 = Long.MAX_VALUE + 1;
		System.out.println("long1=" + long1 + ",binary=" + Long.toBinaryString(long1));
		
		long long2 = 1L << 63;
		System.out.println("long2=" + long2 + ",binary=" + Long.toBinaryString(long2));
	}

}
