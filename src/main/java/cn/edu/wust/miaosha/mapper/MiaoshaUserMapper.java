package cn.edu.wust.miaosha.mapper;

import cn.edu.wust.miaosha.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: huhan
 * @Date 2020/6/23 10:45 上午
 * @Description
 * @Verion 1.0
 */
@Mapper
@Repository
public interface MiaoshaUserMapper {
    @Select("select * from miaosha_user where id=#{id}")
    public MiaoshaUser getById(@Param("id") long id);

    @Update("update miaoshao_user set password=#{password} where id=#{id}")
    int updatePassword(MiaoshaUser miaoshaUser);
}
