package cn.edu.wust.miaosha.service.impl;

import cn.edu.wust.miaosha.entity.MiaoshaGoods;
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

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        g.setGoodsName(goods.getGoodsName());
        g.setMiaoshaPrice(goods.getMiaoshaPrice());
        g.setStockCount(goods.getStockCount());
        //减库存
        goodsService.reduceStock(g);
        //插入订单详情
        //插入秒杀订单
        return orderService.createOrder(miaoshaUser,g);
    }
}
