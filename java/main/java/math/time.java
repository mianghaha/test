package math;

public class time {

	public static void main(String[] args) throws InterruptedException {
		int count = 10;
		for(int i = 0; i < count; i++) {
			Thread.sleep(1000);
			System.out.println("i=" + i + ",time=" + System.currentTimeMillis());
		}
	}
}
