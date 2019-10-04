package zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class ZkServer {

	private ZooKeeper zookeeper;
	
	public static String serverPath = "/servers";
	
	@Before
	public void init() throws IOException {
		zookeeper = new ZooKeeper("192.168.29.128",20000,new Watcher() {
			//在线集合
			List<String> list = new ArrayList<String>();
			//下线集合
			List<String> removeList = new ArrayList<String>();
			@Override
			public void process(WatchedEvent event) {
				try {
					List<String> children = zookeeper.getChildren(serverPath, true);
					for (String string : children) {
						if(!list.contains(string)) {
							list.add(string);
							if(event.getType().equals(EventType.NodeChildrenChanged)) {
								System.out.println(string+"上线");
							}
						}
					}
					//下线处理
					for (String string2 : list) {
						if(!children.contains(string2)) {
							System.out.println(string2+"下线");
							removeList.add(string2);
						}
					}
					list.removeAll(removeList);
					removeList.clear();
					System.out.println("=======");
					System.out.println(list);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Test
	public void run() throws KeeperException, InterruptedException {
		Thread.sleep(20*1000);
	}
	
}
