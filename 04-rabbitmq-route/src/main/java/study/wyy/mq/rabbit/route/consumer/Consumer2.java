package study.wyy.mq.rabbit.route.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description: 消费者，只消费路由key是green和black的消息
 * @Date 2020/12/27 2:47 下午
 */
@Slf4j
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq地址
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // 3 设置要连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 4 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 5 获取连接
        Connection connection = connectionFactory.newConnection();
        // 6 创建通道
        Channel channel = connection.createChannel();

        String exchangeName = "direct_demo";
        // 7 通过通道声明交换机
        // 参数一：交换机的名称   参数二：交换机的类型
        channel.exchangeDeclare(exchangeName,"direct");

        // 8 创建一个临时队列
        String queue = channel.queueDeclare().getQueue();
        // 9 基于路由key，绑定交换机和队列，将路由是green和black绑定到这个队列中
        channel.queueBind(queue,exchangeName,"green");
        channel.queueBind(queue,exchangeName,"black");


        // 10 消费消息
        // 参数一：队列的名字
        // 参数二：是否自动确认
        // 参数三：回调函数
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("消费者2 -> {}", new String(body, Charset.defaultCharset()));
            }
        });

    }
}
