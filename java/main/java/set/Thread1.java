package set;

import java.util.concurrent.ConcurrentSkipListSet;

public class Thread1 extends Thread{

	public Thread1(int id, ConcurrentSkipListSet<Long> set){
		this.id = id;
		this.set = set;
	}
	
	private int id;
	private ConcurrentSkipListSet<Long> set;
	
	public void run(){
		while(true){
			for(Long count : set){
				if(count == null){
					System.out.println("threadid=" + id + ",count=" + count);
				}
			}
		}
	}
}
