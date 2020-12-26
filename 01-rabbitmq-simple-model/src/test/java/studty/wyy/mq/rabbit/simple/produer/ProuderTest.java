package studty.wyy.mq.rabbit.simple.produer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/26 2:36 下午
 */
public class ProuderTest {

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置连接mq主机地址
        connectionFactory.setHost("localhost");
        // 3 设置端口号
        connectionFactory.setPort(5672);
        // 4 设置连接的虚拟主机名称
        connectionFactory.setVirtualHost("/rabbit_study");
        // 5 设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");

        // 6 获取链接对象
        Connection connection = connectionFactory.newConnection();
        // 7 获取连接中的通道channel
        Channel channel = connection.createChannel();

        // 8 通道绑定对应的消息队列
        // 参数一：通道的名字；可以在web端创建，也可以等mq自动创建
        // 参数二：定义队列的特性：是否需要持久化，true持久化（mq重启后队列不会删除），false不持久化
        // 参数二：是否独占队列，true表示当前队列只允许当前连接可用
        // 参数四: 是否在消费完成后自动删除队列
        // 参数五：额外附加参数
        channel.queueDeclare("simple", false, false, false, null);

        // 8 发布消息
        // 参数一：交换机，简单模式，不需要交换机，所以传一个空
        // 参数二: 队列名称
        // 参数三：传递消息的额外设置
        // 参数四：消息的内容，也就是消息体
        channel.basicPublish("","simple", MessageProperties.MINIMAL_PERSISTENT_BASIC,"hello rabbit mq".getBytes(Charset.defaultCharset()));

        // 9 关闭
        channel.close();
        connection.close();
    }
}
