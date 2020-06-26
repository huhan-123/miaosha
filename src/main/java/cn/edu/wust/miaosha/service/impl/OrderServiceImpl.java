package cn.edu.wust.miaosha.service.impl;

import cn.edu.wust.miaosha.entity.MiaoshaGoods;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.mapper.OrderMapper;
import cn.edu.wust.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: huhan
 * @Date 2020/6/26 2:29 下午
 * @Description
 * @Verion 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public MiaoshaOrder getOrderInfoByUserIdAndGoodsId(long userId, long goodsId) {
        return orderMapper.getOrderInfoByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, MiaoshaGoods goods) {
        //创建OrderInfo实体
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(miaoshaUser.getId());
        orderInfo.setGoodsId(goods.getGoodsId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setGoodsCount(1);
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderMapper.insert(orderInfo);
        //创建MiaoshaOrder实体
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(miaoshaUser.getId());
        miaoshaOrder.setGoodsId(goods.getGoodsId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
