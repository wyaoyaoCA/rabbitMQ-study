package study.wyy.mq.rabbit.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author by wyaoyao
 * @Description
 * @Date 2020/12/27 6:39 下午
 */
@Component
@Slf4j
public class FanoutConsumer {

    @RabbitListener(
            bindings = {
            @QueueBinding(
                    value = @Queue,
                    // 绑定交换机，name就是交换机的名字，type交换机的类型
                    exchange = @Exchange(name = "fanout_demo",type = "fanout"))
    })
    public void consumer1(String message){
        System.out.println("consumer1 receive message is " + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,exchange = @Exchange(value = "fanout_demo",type = "fanout"))
    })
    public void consumer2(String message){
        System.out.println("consumer2 receive message is " + message);
    }

}
