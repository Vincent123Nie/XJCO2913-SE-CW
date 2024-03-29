package com.cwk.gps.mapper;

import com.cwk.pojo.entity.UserLocation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserLocationMapper {


    void update(UserLocation userLocation);

    @Select("select * from user_location where user_id = #{userId}")
    UserLocation queryById(Long userId);

    @Insert("insert into user_location(user_id, longitude, latitude, update_time)" +
            "values" +
            "(#{userId},#{longitude},#{latitude},#{updateTime})")
    void insert(UserLocation userLocation);
}
