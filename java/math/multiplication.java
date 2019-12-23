package math;

public class multiplication {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		System.out.println("start1=" + time);
		System.out.println("end1=" + (time / 1000 * 1000));
		
		System.out.println("start2=" + time);
		System.out.println("end2=" + ((time / 1000) * 1000));
	}

}
