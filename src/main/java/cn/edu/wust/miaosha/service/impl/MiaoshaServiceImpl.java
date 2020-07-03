package cn.edu.wust.miaosha.service.impl;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.common.exception.GlobalException;
import cn.edu.wust.miaosha.common.redis.MiaoshaKeyPrefix;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.service.MiaoshaService;
import cn.edu.wust.miaosha.service.OrderService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: huhan
 * @Date 2020/6/26 2:58 下午
 * @Description
 * @Verion 1.0
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {
        //减库存
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            //插入订单详情
            //插入秒杀订单
            return orderService.createOrder(miaoshaUser, goods);
        } else {
            //减库存失败证明已经没有库存，需要将该状态存起来，以便判断入队的订单的结果是失败还是正在排队（如果还有库存则是正在排队）
            setGoodsOver(goods.getId());
            throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
        }
    }

    @Override
    public long getMiaoshaResult(MiaoshaUser miaoshaUser, long goodsId) {
        MiaoshaOrder order = orderService.getOrderInfoByUserIdAndGoodsId(miaoshaUser.getId(), goodsId);
        if (order != null) {
            return order.getOrderId();
        }

        boolean isOver = getGoodsOver(goodsId);
        if (isOver){
            return -1;
        }else {
            return 0;
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKeyPrefix.isGoodsOver, String.valueOf(goodsId), true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKeyPrefix.isGoodsOver, String.valueOf(goodsId));
    }
}
