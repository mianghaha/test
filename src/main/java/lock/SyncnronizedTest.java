package lock;

public class SyncnronizedTest {
	private static Object object = new Object();

	public static void main(String[] args) {
		
		final SyncnronizedTest syn = new SyncnronizedTest();
		
		Thread thread2 = new Thread() {
			public void run() {
				syn.test2();
			}
		};
		thread2.start();
		
		Thread thread1 = new Thread() {
			public void run() {
				syn.test1();
			}
		};
		thread1.start();

	}
	
	public void test1() {
		synchronized(object) {
			System.out.println("1");
		}
	}
	
	public void test2() {
		synchronized(object) {
			int i = 0;
			while(i < 3) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("2");
				i++;
			}
		}
	}
}
