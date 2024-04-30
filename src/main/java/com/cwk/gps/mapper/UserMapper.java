package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.UserPageQueryDTO;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.OrderVO;
import com.cwk.pojo.vo.UserVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 用户登陆
     * @param username
     * @return
     */
    @Select("select * from gps.user where username = #{username};")
    User getByUsername(String username);

    @AutoFill(value = OperationType.INSERT_USER)
    void save(User user);


    @Select("select * from gps.user where email = #{email};")
    User getOne(String email);

    @Select("select * from gps.user where id = #{userId};")
    User queryByUserId(Long userId);

    @AutoFill(value = OperationType.UPDATE_USER)
    void update(User user);
    @Select("select avatar from gps.user where id = #{userId};")
    String queryAvatarByUserId(Long userId);

    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);

    @Delete("delete from user where id = #{id}")
    void delete(Long id);
}
