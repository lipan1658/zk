package zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZkTest {

	private ZooKeeper zookeeper;
	public static String BASE_PATH = "/";
	
	@Before
	public void init() throws IOException {
		zookeeper = new ZooKeeper("192.168.29.128", 20*1000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				try {
					List<String> list = zookeeper.getChildren("/", true);
					for (String string : list) {
						System.out.println("节点："+string);
					}
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Test
	public void createPersistent() throws KeeperException, InterruptedException {
		/*
		 * 持久化
		 */
		String string = zookeeper.create(BASE_PATH+"jiangsu", "江苏".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(string);
	}
	
	@Test
	public void createPersistentSequential() throws KeeperException, InterruptedException {
		/*
		 * 持久化顺序 
		 */
		String string = zookeeper.create(BASE_PATH+"henan", "河南".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		System.out.println(string);
	}
	
	@Test
	public void createEphemeral() throws KeeperException, InterruptedException {
		/*
		 * 持久化顺序 
		 */
		String string = zookeeper.create(BASE_PATH+"anhui", "安徽".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(string);
	}
	
	@Test
	public void createEphemeralSequential() throws KeeperException, InterruptedException {
		/*
		 * 持久化顺序 
		 */
		String string = zookeeper.create(BASE_PATH+"shandong", "山东".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(string);
	}
	
	@Test
	public void exists() throws KeeperException, InterruptedException {
		Stat exists = zookeeper.exists(BASE_PATH+"jiangsu", true);
		System.out.println(exists);
	}
	
	@Test
	public void getData() throws KeeperException, InterruptedException {
		byte[] data = zookeeper.getData(BASE_PATH+"jiangsu", true, null);
		System.out.println("数据===="+new String(data));
	}
	
	@Test
	public void setData() throws KeeperException, InterruptedException {
		Stat exists = zookeeper.exists(BASE_PATH+"jiangsu", true);
		Stat stat = zookeeper.setData(BASE_PATH+"jiangsu","江苏2".getBytes(), exists.getVersion());
		System.out.println(stat);
	}
	
	@Test
	public void getChildren() throws KeeperException, InterruptedException {
		List<String> list = zookeeper.getChildren(BASE_PATH+"jiangsu",true);
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	
	
	
}
