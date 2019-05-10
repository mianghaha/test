package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

	private static final int BUF_SIZE=1024;
    private static final int PORT = 6000;
    private static final int TIMEOUT = 3000;
	
	public static void select() {
		Selector selector = null;
		ServerSocketChannel ssc = null;
		try {
			selector = Selector.open();
			ssc = ServerSocketChannel.open();
			ssc.bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			while(true) {
				if(selector.select(TIMEOUT) == 0) {
					System.out.println("==");
					continue;
				}
				Iterator<SelectionKey> its = selector.selectedKeys().iterator();
				while(its.hasNext()) {
					SelectionKey key = its.next();
					if(key.isWritable() && key.isValid()) {
						handleRead(key);
					}else if(key.isReadable() ) {
						handleRead(key);
					}else if(key.isAcceptable()) {
						handleRead(key);
					}else if(key.isConnectable()) {
						System.out.println("client is connecting.");
					}
					its.remove();
				}
			}
			
		}catch(Exception e) {
			
		}finally {
			try{
                if(selector != null){
                    selector.close();
                }
                if(ssc != null){
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
		}
	}
	
	public static void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = sc.read(buf);
		while(bytesRead > 0) {
			buf.flip();
			while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
			System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
		}
		if(bytesRead == -1){
            sc.close();
        }
		
		System.out.println("client reading over,remote.address=" + sc.getRemoteAddress());
	}
	
	public static void handleWirte(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		while(buf.hasRemaining()) {
			sc.write(buf);
		}
		buf.compact();
		System.out.println("client wirtting over,remote.address=" + sc.getRemoteAddress());
	}
	
	public static void handleAccpet(SelectionKey key) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssc.accept();
		sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
		System.out.println("client accpeting over,remote.address=" + sc.getRemoteAddress());
	}
}
