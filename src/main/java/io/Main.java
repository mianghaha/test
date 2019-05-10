package io;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread t1 = new Thread() {
			public void run() {
				NIOClient client1 = new NIOClient();
				client1.client();
			}
		};
		Thread.currentThread().sleep(1000000);
	}

}
