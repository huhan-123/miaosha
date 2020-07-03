package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.common.Result;
import cn.edu.wust.miaosha.common.rabbitmq.MQSender;
import cn.edu.wust.miaosha.common.rabbitmq.MiaoshaMessage;
import cn.edu.wust.miaosha.common.redis.GoodsKeyPrefix;
import cn.edu.wust.miaosha.common.redis.OrderKeyPrefix;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.mapper.ResetMapper;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.service.MiaoshaService;
import cn.edu.wust.miaosha.service.OrderService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: huhan
 * @Date 2020/6/26 2:11 下午
 * @Description
 * @Verion 1.0
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    @Autowired
    private ResetMapper resetMapper;

    private ConcurrentHashMap<Long, Boolean> localOverMap = new ConcurrentHashMap<>();

    /**
     * QPS:1452
     * 5000*10
     *
     * QPS:2516
     * 5000*10
     */
    @PostMapping("do_miaosha")
    @ResponseBody
    public Result<Integer> doMiaosha(@RequestParam("goodsId") long goodsId, MiaoshaUser miaoshaUser) {
        //判断用户是否登录
        if (miaoshaUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，减少redis访问
        if (localOverMap.get(goodsId)) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //预减库存
        Long count = redisService.decr(GoodsKeyPrefix.getMiaoshaGoodsStock, String.valueOf(goodsId));
        if (count < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }


        //判断是否秒已经秒杀到了（同一用户只能秒杀一个相同商品）
        MiaoshaOrder order = orderService.getOrderInfoByUserIdAndGoodsId(miaoshaUser.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //秒杀信息入队
        sender.send(new MiaoshaMessage(miaoshaUser, goodsId));
        return Result.success(0);
/*
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {//库存不足
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否秒已经秒杀到了（同一用户只能秒杀一个相同商品）
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //减库存
        //插入订单详情
        //插入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser, goods);
        return Result.success(orderInfo);
*/
    }

    /**
     * orderId:成功
     * -1：秒杀失败
     * 0：排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(MiaoshaUser miaoshaUser, long goodsId) {
        long result = miaoshaService.getMiaoshaResult(miaoshaUser, goodsId);
        return Result.success(result);
    }

    /**
     * 系统初始化时将秒杀商品库存同步到redis
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        if (goodsVoList != null && goodsVoList.size() > 0) {
            for (GoodsVo goodsVo : goodsVoList) {
                localOverMap.put(goodsVo.getId(), false);
                redisService.set(GoodsKeyPrefix.getMiaoshaGoodsStock, String.valueOf(goodsVo.getId()), goodsVo.getStockCount());
            }
        }
    }

    @GetMapping("/reset")
    @ResponseBody
    public Result<String> reset() {
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        for(GoodsVo goods : goodsList) {
            redisService.set(GoodsKeyPrefix.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        resetMapper.resetOrderInfo();
        resetMapper.resetMiaoshaGoods();
        resetMapper.resetMiaoshaOrder();
        redisService.delete(OrderKeyPrefix.getMiaoshaOrderByUidGid);
        return Result.success("sucess");
    }
}
