package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {
	
	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final Object ob = new Integer(5); 

	public static void main(String[] args) throws InterruptedException {		
		Thread thread1 = new Thread() {
			public void run() {
				try {
					System.out.println("thread1----------------");
					currentThread().sleep(15 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread thread2 = new Thread() {
			public void run() {
				synchronized (ob) {
					try {
						System.out.println("thread2----------------");
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread thread3 = new Thread() {
			public void run() {
				try {
					System.out.println("thread3----------------");
					currentThread().sleep(1 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		//会无限的增加进程以适用多任务启动要求，在有多余活动进程没用时会重用进程
		ExecutorService executorService1 = Executors.newCachedThreadPool();	
		executorService1.execute(thread1);
		executorService1.execute(thread3);
		
//		ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool(1);
//		executorService2.schedule(thread1, 1, TimeUnit.SECONDS);
//		executorService2.schedule(thread2, 1, TimeUnit.SECONDS);
		
		Thread.currentThread().sleep(100 * 1000);
	}
	
}
