package com.cwk.gps.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface ActivityUserMapper {

    @Delete("delete from activity_user where activity_id = #{activityId}")
    void deleteByActivityId(Long activityId);
}
