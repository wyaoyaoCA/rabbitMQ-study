package study.wyy.mq.rabbit.boot.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 6:39 下午
 */
@Component
// 使用RabbitListener标注消费者
// queuesToDeclare属性用来声明队列
// @Queue注解就是用来标注队列的属性，比如是否持久化（默认就是持久化），是否独占（默认非独占）,是否自动删除
// @Queue注解默认创建的队列是持久化的，非独占，不是自动删除的
@RabbitListener(queuesToDeclare = {@Queue(name = "hello-queue",durable = "false",autoDelete = "false")})
public class HelloWorldConsumer {

    // 使用RabbitHandler注解标明是消费者的回调函数
    @RabbitHandler
    public void consumer(String message){
        System.out.println("receive message is " + message);
    }

}
