package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NIOClient {
	private final static AtomicInteger integer = new AtomicInteger();
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			public void run() {
				NIOClient client1 = new NIOClient();
				client1.client();
			}
		};
		t1.start();
		
		Thread t2 = new Thread() {
			public void run() {
				NIOClient client1 = new NIOClient();
				client1.client();
			}
		};
		t2.start();
	}

	public void client() {
		int id = integer.getAndIncrement();
		System.out.println("NIOClient-" + id + " start.");
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try
        {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",6000));
            if(socketChannel.finishConnect())
            {
                for(int i = 0; i < 3; i++) {
                    TimeUnit.SECONDS.sleep(10);
                    String info = "My id is " + id + ",I'm "+ i + "-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                    Thread.sleep(1000);
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
	}
}
