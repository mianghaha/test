package generateid;

public class TestThread extends Thread{
	private  SnowflakeIdWorker idWorker;
	private int count = 0;
	private int threadid;

	public TestThread(SnowflakeIdWorker idWorker, int threadid){
		this.idWorker = idWorker;
		this.threadid = threadid;
	}
	
	public void run(){
		while(count < 10000){
			count++;
			long generateid = idWorker.nextId();
			System.out.println("threadid=" + threadid + ",generateid=" + generateid);
		}
	}
}
