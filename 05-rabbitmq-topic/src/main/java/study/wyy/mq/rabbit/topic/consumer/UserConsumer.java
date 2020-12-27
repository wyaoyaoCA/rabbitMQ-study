package study.wyy.mq.rabbit.topic.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 4:06 下午
 */
@Slf4j
public class UserConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq地址
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // 3 设置虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 4 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 5 获取连接
        Connection connection = connectionFactory.newConnection();
        // 6 获取通道
        Channel channel = connection.createChannel();
        // 7 声明交换机
        channel.exchangeDeclare("topic_demo","topic");
        // 8 创建队列
        String queue = channel.queueDeclare().getQueue();
        // 9 绑定队列和交换机
        // 参数一：队列名称
        // 参数二：交换机名称
        // 参数三：路由key
        channel.queueBind(queue,"topic_demo","user.*");
        // 8 消费消息
        // 参数一：队列名称
        // 参数二：是否自动确认
        // 参数三：回调接口
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("userConsumer -> {}",new String(body, Charset.defaultCharset()));
            }
        });
    }
}
