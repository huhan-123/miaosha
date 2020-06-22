package cn.edu.wust.miaosha.service;

import cn.edu.wust.miaosha.entity.User;
import cn.edu.wust.miaosha.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: huhan
 * @Date 2020/6/22 10:19 上午
 * @Description
 * @Verion 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    @Transactional
    public int tx() {
        User user1 = new User();
        user1.setId(2);
        user1.setName("zhangsan");
        User user2 = new User();
        user2.setName("wangwu");
        user2.setId(1);
        userMapper.insert(user1);
        userMapper.insert(user2);
        return 2;
    }
}
