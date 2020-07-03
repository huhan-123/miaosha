package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/28 9:17 下午
 * @Description
 * @Verion 1.0
 */
public class OrderKeyPrefix extends BaseKeyPrefix {
    public OrderKeyPrefix(String prefix) {
        super(prefix);
    }

    public static final OrderKeyPrefix getMiaoshaOrderByUidGid = new OrderKeyPrefix("moug");
}
