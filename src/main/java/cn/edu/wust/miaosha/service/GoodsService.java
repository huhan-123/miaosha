package cn.edu.wust.miaosha.service;

import cn.edu.wust.miaosha.entity.Goods;
import cn.edu.wust.miaosha.entity.MiaoshaGoods;
import cn.edu.wust.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/26 9:07 上午
 * @Description
 * @Verion 1.0
 */
public interface GoodsService {
    List<GoodsVo> getGoodsVoList();

    GoodsVo getGoodsVoById(long goodsId);

    int reduceStock(MiaoshaGoods goods);
}
