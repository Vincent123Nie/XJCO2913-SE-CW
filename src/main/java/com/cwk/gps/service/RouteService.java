package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.pojo.dto.QueryHistoryByDateDTO;
import com.cwk.pojo.dto.QueryHistoryDTO;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.vo.NearRouteVo;
import com.cwk.pojo.vo.NearUsersVo;
import com.cwk.pojo.vo.TotalMotionVo;

import java.util.List;

public interface RouteService {
    Long createRoute();

    Boolean deleteRoute(Long routeId);

    Result updateRoute(Long routeId, String title);

    Route queryRouteById(Long routeId);

    Boolean shareRoute(Long routeId);

    List<NearRouteVo> queryNearRoute(Double longitude, Double latitude, Double distance);

    NearUsersVo queryRouteNearUser(Long routeId);

    Long runFromRoute(Long routeId);

    PageResult queryHistoryRoute(QueryHistoryDTO queryHistoryDTO);

    TotalMotionVo queryUserMotionInfo();
}
