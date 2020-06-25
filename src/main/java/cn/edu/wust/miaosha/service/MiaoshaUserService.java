package cn.edu.wust.miaosha.service;

import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huhan
 * @Date 2020/6/23 10:47 上午
 * @Description
 * @Verion 1.0
 */
public interface MiaoshaUserService {
    MiaoshaUser getById(long id);

    boolean login(HttpServletResponse response, LoginVo loginVo);

    MiaoshaUser getByToken(HttpServletResponse response, String token);
}
