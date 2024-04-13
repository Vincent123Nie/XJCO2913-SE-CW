package com.cwk.gps.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 用户登陆
     * @param username
     * @return
     */
    @Select("select * from gps.users where username = #{username};")
    User getByUsername(String username);

    @AutoFill(value = OperationType.INSERT)
    void save(User user);


    @Select("select * from gps.users where email = #{email};")
    User getOne(String email);

    @Select("select * from gps.users where id = #{userId};")
    User queryByUserId(Long userId);

    @AutoFill(value = OperationType.UPDATE)
    void update(User user);
}
