package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class NIOClient2 {
	private final static AtomicInteger integer = new AtomicInteger();
	private final static int bufferSize = 256;
	private final static Charset utf8 = Charset.forName("UTF-8");
    private static final String CHARSET = "UTF-8";
	
	InetSocketAddress remoteAddress;
    Selector selector;
    String name;
    
    public NIOClient2(String name, InetSocketAddress remoteAddress){
    	this.remoteAddress = remoteAddress;
    	this.name = name;
    }
	
	public static void main(String[] args){
		InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 6000);
		
		int id = integer.incrementAndGet();
		Thread t1 = new Thread() {
			public void run() {
				NIOClient2 client1 = new NIOClient2("client" + id, remoteAddress);
				try {
					client1.client();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.start();
		
//		Thread t2 = new Thread() {
//			public void run() {
//				NIOClient2 client1 = new NIOClient2();
//				client1.client();
//			}
//		};
//		t2.start();
	}

	public void client() throws IOException {
        try {
	        /*创建TCP通道*/
	        SocketChannel sc = SocketChannel.open();
	         
	        /*设置通道为非阻塞*/
	        sc.configureBlocking(false);
	         
	        /*创建选择器*/
	        selector = Selector.open();
	        
            /*注册感兴趣事件*/
            int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	        
            /*向选择器注册通道*/
            sc.register(selector, interestSet, new Buffers(bufferSize, bufferSize));
            
            /*向服务器发起连接,一个通道代表一条tcp链接*/
            sc.connect(remoteAddress);
            
            /*等待三次握手完成*/
            while(!sc.finishConnect()){
                ;
            }
            
            System.out.println(name + " finished connection");	        
        }catch (Exception e) {
        	System.out.println("client "  + name + " connect failed");
			e.printStackTrace();
		}
        
        /*与服务器断开或线程被中断则结束线程*/
        try{

            int i = 1;
            while(!Thread.currentThread().isInterrupted()){
                 
                /*阻塞等待*/
                selector.select();
                 
                /*Set中的每个key代表一个通道*/
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();
                 
                /*遍历每个已就绪的通道，处理这个通道已就绪的事件*/
                while(it.hasNext()){
                     
                    SelectionKey key = it.next();
                    /*防止下次select方法返回已处理过的通道*/
                    it.remove();
                     
                    /*通过SelectionKey获取对应的通道*/
                    Buffers buffers = (Buffers)key.attachment();
                    ByteBuffer readBuffer = buffers.getReadBuffer();
                    ByteBuffer writeBuffer = buffers.getWriteBuffer();
                     
                    /*通过SelectionKey获取通道对应的缓冲区*/
                    SocketChannel sc = (SocketChannel) key.channel();
                     
                    /*表示底层socket的读缓冲区有数据可读*/
                    if(key.isReadable()){
                        /*从socket的读缓冲区读取到程序定义的缓冲区中*/
                        sc.read(readBuffer);
                        readBuffer.flip();
                        /*字节到utf8解码*/
                        CharBuffer cb = utf8.decode(readBuffer);
                        /*显示接收到由服务器发送的信息*/
                        System.out.println(cb.array());
                        readBuffer.clear();
                    }
                     
                    /*socket的写缓冲区可写*/
                    if(key.isWritable()){
                        writeBuffer.put((name + "  send message:i=" + i).getBytes("UTF-8"));
                        writeBuffer.flip();
                        /*将程序定义的缓冲区中的内容写入到socket的写缓冲区中*/
                        while(writeBuffer.hasRemaining()){
                        	sc.write(writeBuffer);
                        }
                        writeBuffer.clear();
                        i++;
                    }
                }
                Thread.sleep(1000);
            }
         
        }catch(InterruptedException e){
            System.out.println(name + " is interrupted");
        }catch(IOException e){
            System.out.println(name + " encounter a connect error");
        }finally{
            try {
                selector.close();
            } catch (IOException e1) {
                System.out.println(name + " close selector failed");
            }finally{
                System.out.println(name + "  closed");
            }
        }
	}
}
