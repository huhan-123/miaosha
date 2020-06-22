package cn.edu.wust.miaosha.service;

import cn.edu.wust.miaosha.entity.User;

/**
 * @Author: huhan
 * @Date 2020/6/22 10:19 上午
 * @Description
 * @Verion 1.0
 */
public interface UserService {
    User getById(int id);

    int tx();
}
