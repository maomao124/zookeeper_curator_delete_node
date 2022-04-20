import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Project name(项目名称)：zookeeper_curator删除节点
 * Package(包名): PACKAGE_NAME
 * Class(类名): ZooKeeper
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/20
 * Time(创建时间)： 22:05
 * Version(版本): 1.0
 * Description(描述)： 无
 */


public class ZooKeeper
{
    private CuratorFramework client;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp()
    {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
        //zookeeper创建链接，第一种
                        /*
                        CuratorFramework client =
                                CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                                        60 * 1000,
                                        15 * 1000,
                                        retryPolicy);
                        client.start();
                        */

        //zookeeper创建链接，第二种
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("test")
                .build();
        client.start();
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown()
    {
        if (client != null)
        {
            client.close();
        }
    }

    /**
     * Test 1.
     *
     * @throws Exception the exception
     */
    @Test
    void test1() throws Exception
    {
        client.delete().forPath("/app1");
    }

    /**
     * Test 2.
     *
     * @throws Exception the exception
     */
    @Test
    void test2() throws Exception
    {
        client.delete().deletingChildrenIfNeeded().forPath("/app5/a1");
    }

    /**
     * Test 3.
     *
     * @throws Exception the exception
     */
    @Test
    void test3() throws Exception
    {
        client.delete().guaranteed().forPath("/app2");
    }

    /**
     * Test 4.
     *
     * @throws Exception the exception
     */
    @Test
    void test4() throws Exception
    {
        client.delete().guaranteed().inBackground(new BackgroundCallback()
        {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception
            {
                System.out.println("删除");
                System.out.println(curatorEvent);
            }
        }).forPath("/app3");
    }
}
