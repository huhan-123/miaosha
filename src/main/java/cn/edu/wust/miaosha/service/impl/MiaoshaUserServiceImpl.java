package cn.edu.wust.miaosha.service.impl;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.common.exception.GlobalException;
import cn.edu.wust.miaosha.common.redis.MiaoShaUserKeyPrefix;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.common.util.MD5Util;
import cn.edu.wust.miaosha.common.util.UUIDUtil;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.mapper.MiaoshaUserMapper;
import cn.edu.wust.miaosha.service.MiaoshaUserService;
import cn.edu.wust.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huhan
 * @Date 2020/6/23 10:48 上午
 * @Description
 * @Verion 1.0
 */
@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPassword = user.getPassword();
        if (!dbPassword.equals(MD5Util.formPassToDBPass(password, user.getSalt()))) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, user, token);
        return true;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoShaUserKeyPrefix.token, token, MiaoshaUser.class);
        //每次访问服务端都需要更新token过期时间
        if (user != null) {
            addCookie(response, user, token);
        }
        return user;
    }

    @Override
    public MiaoshaUser getById(long id) {
        return miaoshaUserMapper.getById(id);
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user, String token) {
        //将token，用户信息放入redis缓存
        redisService.set(MiaoShaUserKeyPrefix.token, token, user);
        //将token放入cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoShaUserKeyPrefix.token.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
