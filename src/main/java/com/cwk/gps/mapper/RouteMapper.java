package com.cwk.gps.mapper;

import com.cwk.pojo.dto.QueryHistoryDTO;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.vo.NearRouteVo;
import com.cwk.pojo.vo.RouteVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouteMapper {

    void save(Route route);

    @Select("select * from gps.route where id = #{routeId}")
    Route queryById(Long routeId);

    @Delete("delete from gps.route where id = #{routeId}")
    void deleteById(Long routeId);

    void update(Route route);

    @Select("select * from gps.route where status = 0 and isShare = true")
    List<Route> queryNearRoute();

    Page<RouteVo> OwnpageQuery(QueryHistoryDTO queryHistoryDTO);

    @Select("select * from gps.route where status = 0 and isShare = true and userId = #{userid}")
    Page<RouteVo> OtherpageQuery(QueryHistoryDTO queryHistoryDTO);

    @Select("select count(*) from gps.route where route.endTime between #{minTime} and #{maxTime}")
    int queryRouteCountByDate();

    @Select("select * from gps.route where userId = #{userId} and route.endTime between #{minTime} and #{maxTime}")
    List<Route> queryRouteListByDate(Long userId, Long minDate, Long maxDate);
    @Select("select * from gps.route where userId = #{userId}")
    List<Route> queryByUserId(Long userId);
}

