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
    public String login(HttpServletResponse response, LoginVo loginVo) {
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
        String s = MD5Util.formPassToDBPass(password, user.getSalt());
        if (!dbPassword.equals(MD5Util.formPassToDBPass(password, user.getSalt()))) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, user, token);
        return token;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String s = MiaoShaUserKeyPrefix.getByToken + token;
        MiaoshaUser user = redisService.get(MiaoShaUserKeyPrefix.getByToken, token, MiaoshaUser.class);
        //每次访问服务端都需要更新token过期时间
        if (user != null) {
            addCookie(response, user, token);
        }
        return user;
    }

    @Override
    public boolean updatePassword(long id, String password,String token) {
        MiaoshaUser miaoshaUser = getById(id);
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String convertedPass = MD5Util.formPassToDBPass(password, miaoshaUser.getSalt());
        //如果两个密码不一样，则需要更改密码，同时删除缓存
        if (!convertedPass.equals(miaoshaUser.getPassword())) {
            //这里必须先更新数据库，再删除缓存
            miaoshaUser.setPassword(convertedPass);
            miaoshaUserMapper.updatePassword(miaoshaUser);
            redisService.delete(MiaoShaUserKeyPrefix.getById, String.valueOf(id));
            redisService.delete(MiaoShaUserKeyPrefix.getByToken,token);
        }
        return true;
    }

    @Override
    public MiaoshaUser getById(long id) {
        MiaoshaUser miaoshaUser = redisService.get(MiaoShaUserKeyPrefix.getById, "" + id, MiaoshaUser.class);
        if (miaoshaUser != null) {
            return miaoshaUser;
        }

        miaoshaUser = miaoshaUserMapper.getById(id);
        if (miaoshaUser != null) {
            redisService.set(MiaoShaUserKeyPrefix.getById, "" + id, miaoshaUser);
        }
        return miaoshaUser;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user, String token) {
        //将token，用户信息放入redis缓存
        redisService.set(MiaoShaUserKeyPrefix.getByToken, token, user);
        //将token放入cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoShaUserKeyPrefix.getByToken.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
