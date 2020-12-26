package study.wyy.mq.rabbit.workqueue.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author by wyaoyao
 * @Description 消息生产者
 * @Date 2020/12/26 5:18 下午
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 构建连接工厂，多写几遍熟悉API
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2 设置mq主机地址
        connectionFactory.setHost("localhost");
        // 3 设置端口
        connectionFactory.setPort(5672);
        // 4 设置连接的虚拟主机
        connectionFactory.setVirtualHost("/rabbit_study");
        // 5 设置用户名和密码
        connectionFactory.setUsername("wyy");
        connectionFactory.setPassword("wyy123");

        // 6 创建链接对象
        Connection connection = connectionFactory.newConnection();
        // 7 获取通道
        Channel channel = connection.createChannel();
        // 8 绑定消息队列
        // 参数一：队列名称，rabbit会进行自动创建
        // 参数二：是否持久化
        // 参数三：是否是独占队列
        // 参数四: 是否在消费完成后删除队列
        // 参数五：额外附加参数
        channel.queueDeclare("work-queue", false, false, false, null);

        // 9 发送消息，为了测试通过循环多发几条消息
        for (int i = 1; i <= 20; i++){
            // 参数一：交换机，这个模式也不需要交换机
            // 参数二：队列名称
            // 参数三: 传递消息的额外设置
            // 参数四：消息内容
            channel.basicPublish("","work-queue",null,("hello work queue " + i).getBytes(Charset.defaultCharset()));
        }
        // 关闭资源
        channel.close();
        connection.close();
    }
}
