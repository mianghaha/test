package threadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SychronizedTest {

	public final static SychronizedTest sychronizedTest = new SychronizedTest();
	
	public static void main(String[] args) {
		for(int i = 0; i < 2; i++) {
			Thread t1 = new Thread() {
				@Override
				public void run() {
					try {
						sychronizedTest.test1();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			t1.start();
		}
		
		for(int i = 0; i < 2; i++) {
			Thread t1 = new Thread() {
				@Override
				public void run() {
					try {
						sychronizedTest.test2();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			t1.start();
		}
		
		Collections.synchronizedList(new ArrayList<Integer>());
	}
	
	public synchronized void test1() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ",test1,start");
		Thread.currentThread().sleep(3000);
		System.out.println(Thread.currentThread().getName() + ",test1,end");
	}
	
	public synchronized void test2() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ",test2,start");
		Thread.currentThread().sleep(3000);
		System.out.println(Thread.currentThread().getName() + ",test2,end");
	}
}
