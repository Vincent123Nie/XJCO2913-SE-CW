package com.cwk.gps.mapper;

import com.cwk.pojo.entity.Activity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface ActivityUserMapper {

    @Delete("delete from activity_user where activity_id = #{activityId}")
    void deleteByActivityId(Long activityId);

    @Insert("insert into activity_user(activity_id, user_id)" +
            "values " +
            "(#{id},#{currentId})")
    void insert(Long id, Long currentId);

    @Select("select * from activity_user where user_id = #{currentId}")
    List<Long> queryByUserId(Long currentId);
}
