package cn.edu.wust.miaosha.mapper;

import cn.edu.wust.miaosha.entity.MiaoshaGoods;
import cn.edu.wust.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/26 8:51 上午
 * @Description
 * @Verion 1.0
 */
@Mapper
@Repository
public interface GoodsMapper {
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from goods g left join miaosha_goods mg on g.id=mg.goods_id")
    List<GoodsVo> getGoodsVoList();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from goods g left join miaosha_goods mg on g.id=mg.goods_id where g.id=#{goodsId}")
    GoodsVo getGoodsVoById(long goodsId);

    @Update("update miaosha_goods set stock_count=#{stockCount}-1 where goods_id=#{goodsId}")
    int reduceStock(MiaoshaGoods goods);
}
