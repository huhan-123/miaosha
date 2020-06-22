package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/22 4:03 下午
 * @Description
 * @Verion 1.0
 */
public interface KeyPrefix {
    int getExpireSeconds();

    String getPrefix();
}
