package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.common.Result;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.service.MiaoshaUserService;
import cn.edu.wust.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: huhan
 * @Date 2020/6/21 8:41 下午
 * @Description
 * @Verion 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);
    }

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser miaoshaUser) {
        return Result.success(miaoshaUser);
    }
}
