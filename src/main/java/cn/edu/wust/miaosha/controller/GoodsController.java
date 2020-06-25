package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/to_list")
    public String toList(MiaoshaUser user, Model model) {
        model.addAttribute("user", user);
        return "goods_list";
    }

}
