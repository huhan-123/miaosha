package cn.edu.wust.miaosha.common.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huhan
 * @Date 2020/7/2 9:05 上午
 * @Description
 * @Verion 1.0
 */
@Configuration
public class MQConfig {
    public static final String ROUTING_KEY = "miaosha.queue";
    public static final String DIRECT_EXCHANGE = "directExchange";
    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(MIAOSHA_QUEUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding bindingDirectExchange() {
        return BindingBuilder.bind(miaoshaQueue()).to(directExchange()).with(ROUTING_KEY);
    }
}
