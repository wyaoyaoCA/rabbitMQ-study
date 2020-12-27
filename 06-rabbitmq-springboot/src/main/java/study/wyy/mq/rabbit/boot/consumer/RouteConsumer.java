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
public class RouteConsumer {

    @RabbitListener(
            bindings = {
            @QueueBinding(
                    value = @Queue,
                    // 绑定交换机，name就是交换机的名字，type交换机的类型
                    exchange = @Exchange(name = "route_demo",type = "direct"),
                    key = {"orange"}
            )
    })
    public void consumerOrange(String message){
        System.out.println("consumerOrange receive message is " + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "route_demo",type = "direct"),
                    key = {"green"}
            )
    })
    public void consumerGreen(String message){
        System.out.println("consumerGreen receive message is " + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "route_demo",type = "direct"),
                    key = {"orange","green","black"}
            )
    })
    public void consumerAll(String message){
        System.out.println("consumerAll receive message is " + message);
    }
}
