package cn.edu.wust.miaosha.common.rabbitmq;

import cn.edu.wust.miaosha.entity.MiaoshaUser;

/**
 * @Author: huhan
 * @Date 2020/7/3 9:28 上午
 * @Description
 * @Verion 1.0
 */
public class MiaoshaMessage {
    private MiaoshaUser miaoshaUser;

    private long goodsId;

    public MiaoshaMessage(MiaoshaUser miaoshaUser, Long goodsId) {
        this.miaoshaUser = miaoshaUser;
        this.goodsId = goodsId;
    }

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
