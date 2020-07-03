package cn.edu.wust.miaosha.common.util;

import cn.edu.wust.miaosha.common.rabbitmq.MiaoshaMessage;
import cn.edu.wust.miaosha.entity.MiaoshaUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author: huhan
 * @Date 2020/7/3 12:03 下午
 * @Description
 * @Verion 1.0
 */
public class StringBeanUtil {

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSONObject.parseObject(str, clazz);
        }
    }

    public static void main(String[] args) {
        MiaoshaUser miaoshaUser = new MiaoshaUser();
        miaoshaUser.setId(15671601816L);
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage(miaoshaUser, 1L);
//        String str = "{\"goodsId\":1,\"miaoshaUser\":{\"id\":15671601816,\"lastLoginDate\":1592878876000,\"loginCount\":1,\"nickname\":\"huhan\",\"password\":\"6f4ec94a9915dda3df7c2104ef7ea889\",\"registerDate\":1592878888000,\"salt\":\"2f4h5a6d\"}}";
        MiaoshaMessage miaoshaMessage1 = stringToBean(beanToString(miaoshaMessage), MiaoshaMessage.class);
        System.out.println();
    }
}
