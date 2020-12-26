package studty.wyy.mq.rabbit.simple.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description 消息消费
 * @Date 2020/12/26 3:19 下午
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq的主机地址
        connectionFactory.setHost("localhost");
        // 3 设置端口
        connectionFactory.setPort(5672);
        // 4 设置连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 5 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");

        // 6 获取链接对象
        Connection connection = connectionFactory.newConnection();
        // 7 获取通道
        Channel channel = connection.createChannel();
        // 8 通道绑定队列，主要保证和生产者设置的参数保持一直
        channel.queueDeclare("simple", false, false, false, null);

        // 9 消费消息
        // 参数一：消费哪个队列
        // 参数二：是否开启消息的自动确认机制
        // 参数三：回调接口：把channel传进去  rabbit 默认提供了一个实现类DefaultConsumer
        channel.basicConsume("simple", true, new DefaultConsumer(channel) {
            // 参数一：消息的标签
            // 参数二：消息传递过程中的信封
            // 参数三：发送消息时传的一些属性
            // 参数四：消息内容
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // super.handleDelivery(consumerTag, envelope, properties, body); 内部是个空方法
                // 打印下参数
                log.info("consumerTag: {}", consumerTag);
                log.info("envelope: {}", envelope);
                log.info("properties: {}", properties);
                log.info("body: {}", new String(body, Charset.defaultCharset()));
            }
        });

        // 10 关闭 这里就不要直接关闭，会导致消息收不到，消费者是个监听者，要等待消息的到来
        // channel.close();
        // connection.close();


    }
}
