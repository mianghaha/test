package zookeeper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.CancelLeadershipException;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;

public class CuratorTest3 {
	
	private static CuratorFramework client = CuratorFrameworkFactory.newClient("10.236.254.201:2181", new RetryNTimes(10, 5000));
	private static AtomicInteger count = new AtomicInteger(); 
	private static List<LeaderLatch> latchList = new ArrayList<LeaderLatch>();
	
	public static void main(String[] args)
	{
	    try{
		    client.start();// 连接
		    int amount = 10000;
		    for(int i = 0; i < amount; i++) {
		    	Thread thread = new Thread() {
		    		public void run() {
		    		    try {
							//LeaderSelectorTest();
		    		    	LeaderLatchTest();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    	};
		    	thread.start();
		    }
		    
    		Thread.sleep(10000);
		    
		    while(true) {
		    	for(LeaderLatch latch : latchList) {
		    		System.out.println(latch.getId() + ":hasLeadership=" + latch.hasLeadership() + ",date=" + new Date());
		    	}
	    		Thread.sleep(2000);
		    }

	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public static void LeaderSelectorTest () throws Exception{
		String path = "/test3";
		int id = count.incrementAndGet();
		String name = "client" + id;
		
		LeaderSelector leaderSelector = new LeaderSelector(client, path, new LeaderSelectorListener() {
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				while(true) {
					System.out.println(name + ":I am leader.");
					Thread.sleep(2000);
				}
			}

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				if(newState == ConnectionState.SUSPENDED || newState == ConnectionState.LOST){//处理session过期
					System.out.println(name + ":stateChanged,newState=" + newState);
					throw new CancelLeadershipException();
				}
			}
		});

		leaderSelector.autoRequeue();
		leaderSelector.start();
	}
	
	public static void LeaderLatchTest () throws Exception{
		String path = "/test3";
		int id = count.incrementAndGet();
		String name = "client" + id;
		
		LeaderLatch latch = new LeaderLatch(client, path + name, name);
		System.out.println(latch.getId() + ":add latch at list." + ",date=" + new Date());
		latchList.add(latch);
		
		latch.addListener(new LeaderLatchListener() {
			@Override
			public void isLeader() {
				// TODO Auto-generated method stub
				System.out.println(name + ":I am leader1." + ",date=" + new Date());
			}

			@Override
			public void notLeader() {
				// TODO Auto-generated method stub
				System.out.println(name + ":I am not leader1." + ",date=" + new Date());
			}
		});
		
		latch.addListener(new LeaderLatchListener() {
			@Override
			public void isLeader() {
				// TODO Auto-generated method stub
				System.out.println(name + ":I am leader2." + ",date=" + new Date());
			}

			@Override
			public void notLeader() {
				// TODO Auto-generated method stub
				System.out.println(name + ":I am not leader2." + ",date=" + new Date());
			}
		});

		latch.start();
	}

}
