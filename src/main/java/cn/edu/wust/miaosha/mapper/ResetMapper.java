package cn.edu.wust.miaosha.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: huhan
 * @Date 2020/7/3 7:18 上午
 * @Description
 * @Verion 1.0
 */
@Mapper
@Repository
public interface ResetMapper {
    @Delete("delete from order_info")
    int resetOrderInfo();

    @Delete("delete from miaosha_order")
    int resetMiaoshaOrder();

    @Update("update miaosha_goods set stock_count=10")
    int resetMiaoshaGoods();
}
