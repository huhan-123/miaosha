package cn.edu.wust.miaosha.common.rabbitmq;

import cn.edu.wust.miaosha.common.util.StringBeanUtil;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.service.MiaoshaService;
import cn.edu.wust.miaosha.service.OrderService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: huhan
 * @Date 2020/7/2 9:27 上午
 * @Description
 * @Verion 1.0
 */
@Component
@RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
public class MQReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void receive(String message) {
        logger.info("receive message" + message);
        MiaoshaMessage miaoshaMessage = StringBeanUtil.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser miaoshaUser = miaoshaMessage.getMiaoshaUser();
        Long goodsId = miaoshaMessage.getGoodsId();
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {//库存不足
            return;
        }

        //判断是否秒已经秒杀到了（同一用户只能秒杀一个相同商品）
        MiaoshaOrder order = orderService.getOrderInfoByUserIdAndGoodsId(miaoshaUser.getId(), goodsId);
        if (order != null) {
            return;
        }

        //减库存
        //插入订单详情
        //插入秒杀订单
        miaoshaService.miaosha(miaoshaUser, goods);
    }

}
