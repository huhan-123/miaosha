package cn.edu.wust.miaosha.controller;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.common.redis.RedisService;
import cn.edu.wust.miaosha.common.Result;
import cn.edu.wust.miaosha.common.redis.UserKeyPrefix;
import cn.edu.wust.miaosha.entity.User;
import cn.edu.wust.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huhan
 * @Date 2020/6/21 8:41 下午
 * @Description
 * @Verion 1.0
 */
@RestController
public class Demo {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("hello");
    }

    @GetMapping("/thymelef")
    public String thymelef() {
        return "hello";
    }

    @GetMapping("/getuser")
    public Result<User> getUser(int id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @GetMapping("/tx")
    public Result<Integer> tx() {
        int tx = userService.tx();
        return Result.success(tx);
    }

    /*@GetMapping("/redis/get")
    public Result<Long> redisGet() {
        Long v1 = redisService.get("key1", Long.class);
        return Result.success(v1);
    }*/

    @GetMapping("/redis/set")
    public Result<User> redisSet() {
        User user = new User();
        user.setId(4);
        user.setName("hlasdg");
        boolean ret = redisService.set(UserKeyPrefix.getById, "4", user);
        User result = redisService.get(UserKeyPrefix.getById, "4", User.class);
        return Result.success(result);
    }
}
