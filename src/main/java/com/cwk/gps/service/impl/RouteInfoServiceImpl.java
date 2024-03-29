package com.cwk.gps.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.LocationPointMapper;
import com.cwk.gps.mapper.RouteMapper;
import com.cwk.gps.service.BaiduService;
import com.cwk.gps.service.RouteInfoService;
import com.cwk.gps.utils.TimeUtils;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.entity.LocationPoint;
import com.cwk.pojo.vo.RouteQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RouteInfoServiceImpl implements RouteInfoService {
    @Autowired
    private BaiduService baiduService;
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private LocationPointMapper locationPointMapper;

    /**
     * 更新路线数据，查询鹰眼服务中的轨迹数据，更新到Mongodb中
     *
     * @param routeId
     * @param userId
     * @return
     */
    @Async //异步执行
    public CompletableFuture<String> updateRouteInfo(Long routeId, Long userId) {
        //当前是异步执行，是在一个新的线程中，所以需要将用户id存储到当前的线程中，baiduService中才能获取到用户id
        BaseContext.setCurrentId(userId);
        //根据路线id查询路线数据
        Route route = routeMapper.queryById(routeId);
        log.info("{}",route);

        Long startTime = route.getStartTime() / 1000; //开始时间，精确到秒
        Long endTime = route.getEndTime() / 1000; //结束时间，精确到秒

        log.info("{}-{}", startTime, endTime);
        //查询轨迹数据
        RouteQuery routeEntity = this.baiduService.queryEntity(routeId, startTime, endTime);
        log.info("{}",routeEntity);
        if (null == routeEntity) {
            return CompletableFuture.completedFuture("error");
        }

        //计算运动时间
        String time;
        try {
            time = TimeUtils.formatTime(routeEntity.getEndPoint().getLocTime() - routeEntity.getStartPoint().getLocTime());
        } catch (Exception e) {
            time = "00:00";
        }

        //计算平均速度，每个点速度总和 / 轨迹点总数
        Double speed;
        try {
            speed = routeEntity.getPoints().stream().mapToDouble(LocationPoint::getSpeed).sum() / routeEntity.getSize();
            speed = NumberUtil.round(speed, 2).doubleValue();
        } catch (Exception e) {
            speed = 0.00;
        }
        //更新路线的信息
        route.setSize(routeEntity.getSize());
        route.setDistance(routeEntity.getDistance());
        route.setLocLatitude(routeEntity.getStartPoint().getLatitude());
        route.setLocLongitude(routeEntity.getStartPoint().getLongitude());
        route.setTime(time);
        route.setSpeed(speed);
        log.info("测试-{}", route);
        routeMapper.update(route);
        //更新LocaltionPoint表中的信息
        LocationPoint startPoint = routeEntity.getStartPoint();
        LocationPoint endPoint = routeEntity.getEndPoint();
        List<LocationPoint> points = routeEntity.getPoints();

        startPoint.setRoute_id(routeId);
        startPoint.setType(1);
        endPoint.setRoute_id(routeId);
        endPoint.setType(2);
        locationPointMapper.insert(startPoint);
        locationPointMapper.insert(endPoint);
        for (LocationPoint point : points) {
            point.setRoute_id(routeId);
            point.setType(0);
        }
        locationPointMapper.insertBatch(points);

        return CompletableFuture.completedFuture("ok");
    }
}
