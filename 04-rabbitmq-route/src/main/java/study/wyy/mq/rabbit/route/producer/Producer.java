package study.wyy.mq.rabbit.route.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 2:35 下午
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq地址
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // 3 设置要连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 4 设置账户
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 5 获取连接
        Connection connection = connectionFactory.newConnection();
        // 6 创建连接中的通道
        Channel channel = connection.createChannel();

        // 7 通过channel声明交换机
        // 参数一：交换机名称
        // 参数二：交换机类型
        String exchangeName = "direct_demo";
        channel.exchangeDeclare(exchangeName,"direct");

        // 8 发送消息
        // 参数一：交换机的名称
        // 参数二：路由key：
        // 参数三：传递消息的的其他配置，比如是否持久化消息
        // 参数四：消息体
        // 这里就发三条消息
        channel.basicPublish(exchangeName,"orange",null,"current color is orange".getBytes(Charset.defaultCharset()));
        channel.basicPublish(exchangeName,"green",null,"current color is green".getBytes(Charset.defaultCharset()));
        channel.basicPublish(exchangeName,"black",null,"current color is black".getBytes(Charset.defaultCharset()));

        // 关闭资源
        channel.close();
        connection.close();

    }
}
