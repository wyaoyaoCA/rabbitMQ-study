package study.wyy.mq.rabbit.fanout.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import sun.jvm.hotspot.debugger.win32.coff.COFFLineNumber;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 11:05 上午
 */
@Slf4j
public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq的地址
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        // 3 设置连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 4 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");
        // 5 获取连接对象
        Connection connection = connectionFactory.newConnection();
        // 6 获取channel
        Channel channel = connection.createChannel();

        // 7 绑定刚刚的交换机
        channel.exchangeDeclare("fanout_demo","fanout");

        // 8 创建一个临时队列, 返回队列的名字
        String queueName = channel.queueDeclare().getQueue();

        // 9 绑定交换机和队列
        // 参数一：队列的名字
        // 参数二：交换机的名字
        // 参数三：路由的key
        channel.queueBind(queueName,"fanout_demo","");

        // 消费消息
        // 参数一：队列的名字
        // 参数二：是否开启自动化确认
        // 参数三：回调接口
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("消费者1：{}",new String(body, Charset.defaultCharset()));
            }
        });


    }
}
