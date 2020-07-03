package cn.edu.wust.miaosha.common.rabbitmq;

import cn.edu.wust.miaosha.common.util.StringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: huhan
 * @Date 2020/7/2 9:20 上午
 * @Description
 * @Verion 1.0
 */
@Component
public class MQSender {
    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(MiaoshaMessage message) {
        String str = StringBeanUtil.beanToString(message);
        logger.info("send message:" + str);
        rabbitTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE, MQConfig.ROUTING_KEY, str);
    }
}
