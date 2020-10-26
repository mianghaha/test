package threadpool;

public class ThreadStateTest extends Thread{

	public void run() {
		while(true) {
			System.out.println("run");
			break;
//			try {
//				Thread.currentThread().sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadStateTest t = new ThreadStateTest();
		System.out.println("111" + t.getState());
		t.start();
//		System.out.println("222" + t.getState());
//		t.interrupt();
		Thread.currentThread().sleep(5000);
		System.out.println("333" + t.getState());
		t.start();
		System.out.println("444" + t.getState());
	}
}
