package zookeeper;

import org.I0Itec.zkclient.ZkClient;

public class IOItecZooKeeperUtil {
		 
    public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";
 
    public static ZkClient getZkClient() {
        return new ZkClient("10.236.254.23:2181");
    }
	 
}
