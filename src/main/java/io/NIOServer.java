package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NIOServer {
	
	public static void main(String[] args) {
		start();
	}

	private static final int BUF_SIZE = 16;
    private static final int PORT = 6000;
    private static final int TIMEOUT = 3000;
    private static final String CHARSET = "UTF-8";
	
	public static void start() {
		Selector selector = null;
		ServerSocketChannel ssc = null;
		try {
			selector = Selector.open();
			ssc = ServerSocketChannel.open();
			ssc.bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			while(true) {
				int n = selector.select();
                if(n == 0){
                    continue;
                }
				Iterator<SelectionKey> its = selector.selectedKeys().iterator();
				while(its.hasNext()) {
					SelectionKey key = its.next();
					
					if(key.isConnectable()) {
						System.out.println("client is connected.");
					}
					
					if(key.isAcceptable()) {
						handleAccpet(key);
					}
					
					if(key.isReadable() ) {
						handleRead(key);
					}
					
					if(key.isWritable() && key.isValid()) {
						handleWirte(key);
					}
					its.remove();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
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
		Buffers buffers = (Buffers) key.attachment();
		
		ByteBuffer readBuffer = buffers.getReadBuffer();
		long bytesRead = sc.read(readBuffer);
		String info = "";
		int i = 0;
		while(bytesRead > 0) {
			i++;
			readBuffer.flip();
			while(readBuffer.hasRemaining()){
				info = info + (char)readBuffer.get();
            }
			readBuffer.clear();
            bytesRead = sc.read(readBuffer);
		}
		

		if(!info.isEmpty()) {
			System.out.println("client info is:" + info
					+ ",bytesRead=" + bytesRead
					+ ",partNum=" + i
					+ ",remote.address=" + sc.getRemoteAddress() 
					+ ",channel.hashcode=" + sc.hashCode());
            buffers.setResult(info);
            
            /*设置通道写事件*/
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
		}
	}
	
	public static void handleWirte(SelectionKey key) throws IOException {
		Buffers buffers = (Buffers)key.attachment();
		SocketChannel sc = (SocketChannel) key.channel();
		
		ByteBuffer writeBuffer = buffers.getWriteBuffer();
		byte[] res = ("echo from service:" + buffers.getResult()).getBytes(CHARSET);
		int pos = 0;
		int length = res.length;
		while(pos < length) {
			int size = BUF_SIZE;
			if(size > length - pos) {
				size = length - pos;
			}
			writeBuffer.put(res, pos, size);
			pos = pos + size;
			writeBuffer.flip();
            while(writeBuffer.hasRemaining()){
            	sc.write(writeBuffer);
            }
            writeBuffer.clear();
		}
		
		key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
		System.out.println("client wirtting over,remote.address=" + sc.getRemoteAddress());
	}
	
	public static void handleAccpet(SelectionKey key) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssc.accept();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ, new Buffers(BUF_SIZE, BUF_SIZE));
		System.out.println("client accpeting over,remote.address=" + sc.getRemoteAddress());
	}
	
	public ByteBuffer encode(String str){//编码
        return Charset.forName(CHARSET).encode(str);
    }
}
