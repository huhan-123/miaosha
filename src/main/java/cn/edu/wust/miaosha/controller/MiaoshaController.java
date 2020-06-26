package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.entity.MiaoshaOrder;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.entity.OrderInfo;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.service.MiaoshaService;
import cn.edu.wust.miaosha.service.OrderService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: huhan
 * @Date 2020/6/26 2:11 下午
 * @Description
 * @Verion 1.0
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(@RequestParam("goodsId") long goodsId, MiaoshaUser miaoshaUser, Model model) {
        //判断用户是否登录
        if (miaoshaUser == null) {
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {//库存不足
            model.addAttribute("errormsg", CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }

        //判断是否秒已经秒杀到了（同一用户只能秒杀一个相同商品）
        MiaoshaOrder order = orderService.getOrderInfoByUserIdAndGoodsId(miaoshaUser.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errormsg", CodeMsg.REPEATE_MIAOSHA);
            return "miaosha_fail";
        }

        //减库存
        //插入订单详情
        //插入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);

        return "order_detail";
    }
}
