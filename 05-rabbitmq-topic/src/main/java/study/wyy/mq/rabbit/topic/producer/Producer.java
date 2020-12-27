package study.wyy.mq.rabbit.topic.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 3:56 下午
 */
@Slf4j
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq地址
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // 3 设置虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 4 设置用户
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 5 获取连接
        Connection connection = connectionFactory.newConnection();
        // 6 创建通道
        Channel channel = connection.createChannel();

        // 7 声明交换机
        // 参数一：交换机的名字
        // 参数二：交换机的类型
        channel.exchangeDeclare("topic_demo","topic");
        // 8 发送消息
        // 参数一交换机的名字
        // 参数二： 路由key
        // 参数三：额外设置，比如是否持久化数据
        // 参数四：消息体
        String routeKey1 = "user.*";
        channel.basicPublish("topic_demo",routeKey1,null,"user is save".getBytes(Charset.defaultCharset()));
        channel.basicPublish("topic_demo",routeKey1,null,"user is update".getBytes(Charset.defaultCharset()));
        // 再发两条信息，使用的是routeKey2
        String routeKey2 = "item.*";
        channel.basicPublish("topic_demo",routeKey2,null,"item is save".getBytes(Charset.defaultCharset()));
        channel.basicPublish("topic_demo",routeKey2,null,"item is update".getBytes(Charset.defaultCharset()));
        // 9 关闭资源
        channel.close();
        connection.close();

    }
}
