package multithread;

public class ThreadClass1 extends Thread{

	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("ThreadClass1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
