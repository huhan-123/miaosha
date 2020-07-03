package cn.edu.wust.miaosha.common.redis;

public class GoodsKeyPrefix extends BaseKeyPrefix {

    private GoodsKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKeyPrefix getGoodsList = new GoodsKeyPrefix(60, "gl");
    public static GoodsKeyPrefix getGoodsDetail = new GoodsKeyPrefix(60, "gd");
    public static GoodsKeyPrefix getMiaoshaGoodsStock = new GoodsKeyPrefix(0, "gs");
}
