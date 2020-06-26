package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.service.MiaoshaUserService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/23 7:43 下午
 * @Description
 * @Verion 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model) {
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startTime) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) (startTime - now) / 1000;
        } else if (now > endTime) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("user", miaoshaUser);
        model.addAttribute("goods", goods);
        return "goods_detail";
    }

}
