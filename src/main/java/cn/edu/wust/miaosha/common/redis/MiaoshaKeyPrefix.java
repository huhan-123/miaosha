package cn.edu.wust.miaosha.common.redis;

public class MiaoshaKeyPrefix extends BaseKeyPrefix {

    private MiaoshaKeyPrefix(String prefix) {
        super(prefix);
    }

    public static MiaoshaKeyPrefix isGoodsOver = new MiaoshaKeyPrefix("go");
}
