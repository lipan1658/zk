package zk;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class ZkClient {

	private ZooKeeper zookeeper;
	
	public static String serverPath = "/servers";
	
	@Before
	public void connect() throws IOException {
		zookeeper = new ZooKeeper("192.168.29.128",20000,new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				
			}
		});
	}
	
	@Test
	public void register() throws KeeperException, InterruptedException, UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		String hostName = localHost.getHostName();
		String create = zookeeper.create(serverPath+"/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(create);
		Thread.sleep(10*1000);
	}
}
