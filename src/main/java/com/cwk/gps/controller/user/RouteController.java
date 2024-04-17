package com.cwk.gps.controller.user;


import com.cwk.context.BaseContext;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.RouteService;
import com.cwk.gps.utils.DistanceUtils;
import com.cwk.pojo.dto.QueryHistoryByDateDTO;
import com.cwk.pojo.dto.QueryHistoryDTO;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.vo.NearRouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * 路线相关的业务
 */
@RestController
@RequestMapping("/user/route")
@Slf4j
public class RouteController {

    @Autowired
    private RouteService routeService;

    /**
     * 创建路线
     *
     * @return
     */
    @PostMapping
    public Result createRoute() {
        Long routeId = routeService.createRoute();
        log.info("路线的id: {}, {}", routeId, BaseContext.getCurrentId());
        if (null != routeId) {
            //创建成功
            return Result.success(routeId);
        }
        return Result.error("创建路线失败！");
    }

    /**
     * 删除路线
     *
     * @param routeId
     * @return
     */
    @DeleteMapping
    public Result deleteRoute(@RequestParam Long routeId) {
        Boolean result = this.routeService.deleteRoute(routeId);
        if (result) {
            //删除成功
            return Result.success("成功删除");
        }
        //删除失败
        return Result.error("删除路线失败！");
    }

    /**
     * 更新路线（结束运动）
     *
     * @param routeId 路线id
     * @param title   路线标题
     * @return
     */
    @PutMapping
    public Object updateRoute(@RequestParam("routeId") Long routeId, @RequestParam("title") String title) {
        return routeService.updateRoute(routeId, title);
    }

    /**
     * 根据路线id查询路线数据
     *
     * @param routeId   路线id
     * @param longitude 当前用户的经度，用于计算当前用户与该路线的距离
     * @param latitude  当前用户的纬度，用于计算当前用户与该路线的距离
     * @return
     */
    @GetMapping("{routeId}")
    public Result queryRoute(@PathVariable("routeId") Long routeId,
                             @RequestParam("longitude") Double longitude,
                             @RequestParam("latitude") Double latitude) {
        Route route = routeService.queryRouteById(routeId);
        log.info("找到的路线：{}", route);
        if (null != route) {
            //计算当前用户与该路线的距离
            double distance = DistanceUtils.getDistance(longitude, latitude,
                    route.getLocLongitude(),
                    route.getLocLatitude());
            route.setRouteDistance(distance);
            return Result.success(route);
        }
        return Result.error("路线不存在！");
    }

    /**
     * 投稿路线
     *
     * @param routeId
     * @return
     */
    @PutMapping("share/{routeId}")
    public Result shareRoute(@PathVariable("routeId") Long routeId) {
        Boolean result = routeService.shareRoute(routeId);
        if (result) {
            return Result.success("投稿路线成功");
        }
        return Result.error("投稿路线失败！");
    }

    /**
     * 查询附近的路线
     *
     * @param longitude 当前用户所在位置的经度
     * @param latitude  当前用户所在位置的纬度
     * @param distance  查询的距离，单位：km，默认10km
     * @return
     */
    @GetMapping("near")
    public Result queryNearRoute(@RequestParam("longitude") Double longitude,
                                 @RequestParam("latitude") Double latitude,
                                 @RequestParam(value = "distance", defaultValue = "10") Double distance) {
        List<NearRouteVo> list = routeService.queryNearRoute(longitude, latitude, distance);
        return Result.success(list);
    }

    /**
     * 路线同行的人
     *
     * @param routeId 路线id
     * @return
     */
    @GetMapping("nearUser/{routeId}")
    public Result queryRouteNearUser(@PathVariable("routeId") Long routeId) {
        return Result.success(routeService.queryRouteNearUser(routeId));
    }

    /**
     * 沿路线开始运动
     *
     * @param routeId 目标路线id
     * @return 新创建的路线id
     */
    @PostMapping("{routeId}")
    public Result runFromRoute(@PathVariable("routeId") Long routeId) {
        Long myRouteId = routeService.runFromRoute(routeId);
        if (null != myRouteId) {
            return Result.success(myRouteId);
        }
        return Result.error("沿路线开始运动失败！");
    }

    /**
     * 历史路线
     *
     * @param queryHistoryDTO
     * @return
     */
    @GetMapping("history")
    public Result<PageResult> queryHistoryRoute(@RequestBody QueryHistoryDTO queryHistoryDTO) {
        PageResult pageResult = routeService.queryHistoryRoute(queryHistoryDTO);
        return Result.success(pageResult);
    }


}
