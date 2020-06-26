package cn.edu.wust.miaosha.service;

import cn.edu.wust.miaosha.entity.MiaoshaGoods;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.vo.GoodsVo;

/**
 * @Author: huhan
 * @Date 2020/6/26 2:29 下午
 * @Description
 * @Verion 1.0
 */
public interface OrderService {
    MiaoshaOrder getOrderInfoByUserIdAndGoodsId(long userId, long goodsId);

    OrderInfo createOrder(MiaoshaUser miaoshaUser, MiaoshaGoods goods);
}
