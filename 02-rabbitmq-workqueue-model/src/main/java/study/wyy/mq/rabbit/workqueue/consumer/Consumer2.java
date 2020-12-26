package study.wyy.mq.rabbit.workqueue.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/26 7:20 下午
 */
@Slf4j
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq的host地址
        connectionFactory.setHost("localhost");
        // 3 设置端口
        connectionFactory.setPort(5672);
        // 4 设置要连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 5 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");

        // 6 创建连接
        Connection connection = connectionFactory.newConnection();
        // 7 获取渠道
        Channel channel = connection.createChannel();

        channel.basicQos(1);
        // 8 绑定消息队列，消费者这边一定要和生产者那边保持一致
        // 参数一: 队列的名称，mq会根据是否存在进行自动创建
        // 参数二： 是否持久化队列
        // 参数三： 是否是独占队列
        // 参数四：是否在队列内消息消费完成后自动删除队列
        // 参数五：一些其他参数
        channel.queueDeclare("work-queue", false, false, false, null);
        // 设置每次通道中只有一个消息，保证每次只消费一条消息
        channel.basicQos(1);
        // 9 消费消息
        // 参数一：队列名称
        // 参数二：是否开启消息的自动确认机制。 默认是false
        // 参数三：回调接口
        channel.basicConsume("work-queue",false,new DefaultConsumer(channel){
            // 参数一：消费者的标记
            // 参数二：消息的信封
            // 参数三：消息的内容
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("消费者-2: {}", new String(body, Charset.defaultCharset()));
                // 手动确认消息
                // 参数一：消息的标识，可以从Envelope获取
                // 参数二：关闭一次确认多条消息，一次只确认一个
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
