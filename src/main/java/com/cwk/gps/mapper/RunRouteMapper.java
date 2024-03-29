package com.cwk.gps.mapper;

import com.cwk.pojo.entity.RunRoute;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RunRouteMapper {

    //查询正在同一条路线上运动的用户
    @Select("select * from gps.run_route where runRouteId = #{routeId} and status = 1")
    List<RunRoute> queryById(Long routeId);

    @Insert("insert into gps.run_route(runRouteId, routeId, userId, status, created, updated)" +
            "values " +
            "(#{runRouteId},#{routeId},#{userId},#{status},#{created},#{updated})")
    void save(RunRoute runRoute);

    @Delete("delete from gps.run_route where routeId = #{routeId}")
    void delete(Long routeId);

    @Update("update gps.route set status = 1 where id = #{routeId}")
    void update(Long routeId);
}
