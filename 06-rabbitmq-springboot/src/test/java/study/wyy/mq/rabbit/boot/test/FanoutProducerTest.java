package study.wyy.mq.rabbit.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import study.wyy.mq.rabbit.boot.RabbitMqApplication;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 6:27 下午
 */
@SpringBootTest(classes = RabbitMqApplication.class)
@RunWith(SpringRunner.class)
public class FanoutProducerTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        // 参数一：交换机名称
        // 参数二：路由key，这种模式不需要路由key
        // 参数三：消息体
        rabbitTemplate.convertAndSend("fanout_demo","","这是一条广播消息");
    }
}
