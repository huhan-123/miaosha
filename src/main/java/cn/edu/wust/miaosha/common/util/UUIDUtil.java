package cn.edu.wust.miaosha.common.util;

import java.util.UUID;

/**
 * @Author: huhan
 * @Date 2020/6/23 4:47 下午
 * @Description
 * @Verion 1.0
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
