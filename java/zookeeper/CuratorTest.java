package zookeeper;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class CuratorTest {
	
	private static CuratorFramework client = CuratorFrameworkFactory.newClient("10.236.254.201:2181", new RetryNTimes(10, 5000));
	
	
	public static void main(String[] args)
	{
	    try{
		    client.start();// 连接
		    nodeCacheTest();
//		    baseTest();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public static void baseTest() throws Exception{
		final String path = "/test";
		// 获取子节点，顺便监控子节点
//	    List<String> children = client.getChildren().usingWatcher(new CuratorWatcher()
//	    {
//			public void process(WatchedEvent event) throws Exception {
//				// TODO Auto-generated method stub
//	            System.out.println("子节点监控： " + event);
//			}
//	       
//	    }).forPath(path);
//	    System.out.println(children);
		
	    if(client.checkExists().forPath(path) == null){
			String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, "init".getBytes());  
			System.out.println("create result=" + result);
		}
		
	    //watcher都是一次性的 使用后要继续添加
	    client.getData().usingWatcher(new CuratorWatcher()
	    {
			public void process(WatchedEvent event) throws Exception {
				// TODO Auto-generated method stub
				if(event.getType() == EventType.NodeCreated){
					byte[] data = client.getData().usingWatcher(this).forPath(path);
	                System.out.println("data add,data=" + new String(data,"utf-8"));  
				}else if(event.getType() == EventType.NodeDataChanged){  
	                byte[] data = client.getData().usingWatcher(this).forPath(path);
	                System.out.println("data changed,data=" + new String(data,"utf-8"));  
	            }else if(event.getType() == EventType.NodeDeleted){
	            	client.checkExists().usingWatcher(this).forPath(path);
	            	System.out.println("data delete");  
	            }
			}
	    }).forPath(path);
	    
//	    client.getCuratorListenable().addListener(new CuratorListener()
//	    {
//	        @Override
//	        public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception
//	        {
//	            System.out.println("事件： " + event);
//	        }
//	    });
	    
	    // 设置节点数据
	    System.out.println("set data result=" + client.setData().forPath(path, "111".getBytes()));
	    System.out.println("set data result=" + client.setData().forPath(path, "222".getBytes()));
	    
	    //获得节点数据
//	    String data = new String(client.getData().inBackground().forPath(path));
	    String data = new String(client.getData().forPath(path));
	    System.out.println("get data result=" + data);
	    
	    // 删除节点
	    System.out.println("check exist result=" + client.checkExists().forPath(path));
	    client.delete().withVersion(-1).forPath(path);
	    System.out.println("delete data result=" + client.checkExists().forPath(path));
	    
	    //重新创建
	    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, "init".getBytes());  
	    client.close();
	}
	
	public static void nodeCacheTest() throws Exception{
		String path = "/test";
		if(client.checkExists().forPath(path) == null){
			String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, "init".getBytes());  
			System.out.println("create result=" + result);
		}
		
        final NodeCache cache = new NodeCache(client, path, false);
          
        cache.start(true);  
        cache.getListenable().addListener(new NodeCacheListener(){
            public void nodeChanged() throws Exception{ 
            	if(cache.getCurrentData() == null){
            		System.out.println("Node data del, data="+ cache.getCurrentData());  
        		}else{
        			 System.out.println("Node data update,new data="+ new String(cache.getCurrentData().getData()));  
        		}
            }
        });  
        client.setData().forPath(path,"u".getBytes());  
        client.setData().forPath(path,"y".getBytes());  
        Thread.sleep(1000);  
        client.delete().deletingChildrenIfNeeded().forPath(path);
		if(client.checkExists().forPath(path) == null){
			String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, "init".getBytes());  
			System.out.println("create result=" + result);
		}
        Thread.sleep(Integer.MAX_VALUE); 
	}

}
