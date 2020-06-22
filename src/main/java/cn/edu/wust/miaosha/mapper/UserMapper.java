package cn.edu.wust.miaosha.mapper;

import cn.edu.wust.miaosha.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: huhan
 * @Date 2020/6/22 10:01 上午
 * @Description
 * @Verion 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User getById(int id);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    public int insert(User user);
}
