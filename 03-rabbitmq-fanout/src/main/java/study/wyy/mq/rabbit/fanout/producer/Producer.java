package study.wyy.mq.rabbit.fanout.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description: 生产者
 * @Date 2020/12/27 9:45 上午
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 构建连接对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq的主机地址
        connectionFactory.setHost("localhost");
        // 3 设置端口号
        connectionFactory.setPort(5672);
        // 4 设置要连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 5 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 6 创建连接对象
        Connection connection = connectionFactory.newConnection();
        // 7 创建连接中的chanel
        Channel channel = connection.createChannel();
        // 8 声明交换机
        // 参数一：交换机的名称，rabbitMq如果不存在，会自动创建
        // 参数二：交换机的类型，这里就是fanout
        channel.exchangeDeclare("fanout_demo","fanout");

        // 9 发送消息，此时就是把消息投递给了交换机
        // 参数一：交换机的名称
        // 参数二：路由key，在广播模式下，路由key是没有意义的，因为广播，要把消息投递给所有的队列，才能实现广播不是
        // 参数三：传递消息的额外设置
        // 参数四：消息体
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        String message = "一条广播消息 " + format;
        channel.basicPublish("fanout_demo","",null,message.getBytes(Charset.defaultCharset()));
        channel.close();
        connection.close();
    }
}
