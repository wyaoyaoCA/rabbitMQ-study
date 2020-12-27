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
public class WorkQueueConsumer {

    @RabbitListener(queuesToDeclare = {@Queue(name = "work-queue",durable = "false",autoDelete = "false")})
    public void consumer1(String message){
        System.out.println("consumer1 receive message is " + message);
    }


    @RabbitListener(queuesToDeclare = {@Queue(name = "work-queue",durable = "false",autoDelete = "false")})
    public void consumer2(String message){
        System.out.println("consumer2 receive message is " + message);
    }

}
