package cn.edu.wust.miaosha.service.impl;

import cn.edu.wust.miaosha.entity.MiaoshaGoods;
import cn.edu.wust.miaosha.mapper.GoodsMapper;
import cn.edu.wust.miaosha.service.GoodsService;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/26 9:07 上午
 * @Description
 * @Verion 1.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> getGoodsVoList() {
        return goodsMapper.getGoodsVoList();
    }

    @Override
    public GoodsVo getGoodsVoById(long goodsId) {
        return goodsMapper.getGoodsVoById(goodsId);
    }

    @Override
    public int reduceStock(MiaoshaGoods goods) {
        return goodsMapper.reduceStock(goods);
    }
}
