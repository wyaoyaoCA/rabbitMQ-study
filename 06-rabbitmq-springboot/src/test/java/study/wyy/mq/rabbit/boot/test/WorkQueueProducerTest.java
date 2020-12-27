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
public class WorkQueueProducerTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /****
     * Work Queue模式：是不需要交换机的哟
     */
    @Test
    public void testSendMessage() {
        // 参数一：routeKey: WorkQueue模式是直接发到队列中，所以这里就是队列的名字
        // 参数二：消息体，rabbitTemplate已经给封装成一个对象，可以直接传一个对象过去
        for (int i = 1; i < 10; i++) {
            rabbitTemplate.convertAndSend("work-queue", "一条Work Queue模式的消息-->" + i);
        }
    }
}
