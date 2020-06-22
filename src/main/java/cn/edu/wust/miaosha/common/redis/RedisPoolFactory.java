package cn.edu.wust.miaosha.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: huhan
 * @Date 2020/6/22 3:36 下午
 * @Description
 * @Verion 1.0
 */
@Component
public class RedisPoolFactory {
    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        return new JedisPool(jedisPoolConfig,
                redisConfig.getHost(),
                redisConfig.getPort(),
                redisConfig.getTimeout() * 1000,
                redisConfig.getPassword(),
                0);
    }
}
