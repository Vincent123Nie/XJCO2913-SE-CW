package com.cwk.gps.mapper;

import com.cwk.pojo.entity.LocationPoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationPointMapper {

    @Insert("insert into location_point(route_id, longitude, latitude, locTime, speed, type)" +
            "values " +
            "(#{route_id},#{longitude},#{latitude},#{locTime},#{speed},#{type})")
    void insert(LocationPoint startPoint);

    void insertBatch(List<LocationPoint> points);
}
