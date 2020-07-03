package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.common.Result;
import cn.edu.wust.miaosha.common.redis.GoodsKeyPrefix;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.vo.GoodsDetailVo;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    /**
     * QPS:1256  6376（设置页面缓存、对象缓存后）
     * 5000*10
     */
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //取页面缓存
        String html = redisService.get(GoodsKeyPrefix.getGoodsList, "", String.class);
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }

        //缓存为空，手动渲染页面，然后加入缓存
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList", goodsList);
        IContext context = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodsKeyPrefix.getGoodsList, "", html);
        }
        return html;
    }

/*
    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId") long goodsId) {
        String html = redisService.get(GoodsKeyPrefix.getGoodsDetail, "" + goodsId, String.class);
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }

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
        IContext context = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);

        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodsKeyPrefix.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }
*/

    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail (HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId") long goodsId) {
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
        GoodsDetailVo data = new GoodsDetailVo();
        data.setGoods(goods);
        data.setMiaoshaStatus(miaoshaStatus);
        data.setRemainSeconds(remainSeconds);
        data.setUser(miaoshaUser);
        return Result.success(data);
    }
}
