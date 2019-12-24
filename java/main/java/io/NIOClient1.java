package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class NIOClient1 {
	private final static AtomicInteger integer = new AtomicInteger();
	private final static int bufferSize = 12;
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			public void run() {
				NIOClient1 client1 = new NIOClient1();
				client1.client();
			}
		};
		t1.start();
		
		Thread t2 = new Thread() {
			public void run() {
				NIOClient1 client1 = new NIOClient1();
				client1.client();
			}
		};
		t2.start();
	}

	public void client() {
		int id = integer.getAndIncrement();
		System.out.println("NIOClient-" + id + " start.");

		ByteBuffer buffer = ByteBuffer.allocate((int)bufferSize);
        SocketChannel socketChannel = null;
        for(int i = 0; i < 3; i++){
        	try{
	            socketChannel = SocketChannel.open();
	            socketChannel.configureBlocking(false);
	            socketChannel.connect(new InetSocketAddress("127.0.0.1",6000));
	            if(socketChannel.finishConnect()){
                    String info = "My id is " + id + ",I'm "+ i +"-th information from client";
                    byte[] infoBytes = info.getBytes();
                    int count = (int)Math.ceil(infoBytes.length / (float)bufferSize);
                    for(int j = 0; j < count; j++) {
                    	int length = bufferSize;
                    	int start = j * bufferSize;
                    	if(length > infoBytes.length - start) {
                    		length = infoBytes.length - start;
                    	}
                    	buffer.put(infoBytes, start, length);
                    	buffer.flip();
                        while(buffer.hasRemaining()){
                            socketChannel.write(buffer);
                        }
                        buffer.clear();
                    }   
                    System.out.println("info has send,info=" + info);
	            }
	        }catch (Exception e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(socketChannel!=null){
	                    socketChannel.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
