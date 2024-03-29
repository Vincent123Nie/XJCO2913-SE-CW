package com.cwk.gps.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.RouteMapper;
import com.cwk.gps.mapper.RunRouteMapper;
import com.cwk.gps.mapper.UserMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.BaiduService;
import com.cwk.gps.service.RouteInfoService;
import com.cwk.gps.service.RouteService;
import com.cwk.gps.utils.DistanceUtils;
import com.cwk.pojo.dto.QueryHistoryDTO;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.entity.RunRoute;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.NearRouteVo;
import com.cwk.pojo.vo.NearUserVo;
import com.cwk.pojo.vo.NearUsersVo;
import com.cwk.pojo.vo.RouteVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    @Autowired
    private BaiduService baiduService;
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private RunRouteMapper runRouteMapper;
    @Autowired
    private RouteInfoService routeInfoService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createRoute() {
        Route route = new Route();
        log.info("{}", BaseContext.getCurrentId());
        route.setUserId(BaseContext.getCurrentId());
        route.setStatus(1);
        route.setIsShare(false); //默认不投稿
        route.setStartTime(System.currentTimeMillis());
        routeMapper.save(route);
        Long routeId = route.getId();
        //百度地图鹰眼服务中创建Entity
        Boolean bool = baiduService.createEntity(routeId);
        log.info("{}", bool);
        if (bool) {
            //成功
            return routeId;
        }
        //失败
        return null;
    }

    /**
     * 删除路线
     *
     * @param routeId 路线id
     * @return
     */
    public Boolean deleteRoute(Long routeId) {
        Route route = routeMapper.queryById(routeId);
        if (route != null) {
            routeMapper.deleteById(routeId);
            //删除百度鹰眼服务中的实体
            if (baiduService.deleteEntity(routeId)) {
                //删除沿着路线运动的关系数据
                runRouteMapper.delete(routeId);
                return true;
            }
        }
        return false;
    }

    /**
     * 更新路线（结束运动）
     *
     * @param routeId 路线id
     * @param title   路线标题
     * @return
     */
    public Result updateRoute(Long routeId, String title) {
        //判断路线是否已经结束，如果已经结束就不能再次结束
        Route route = routeMapper.queryById(routeId);
        if (null == route) {
            return Result.error("结束运动失败，路线不存在.");
        }

        if (route.getStatus() == 0) {
            return Result.error("结束运动失败，该路线已经结束.");
        }

        //更新路线数据
        route.setTitle(title);
        route.setStatus(0);
        route.setEndTime(System.currentTimeMillis());
        routeMapper.update(route);
        //查询百度地图鹰眼服务中路线轨迹点，更新到路线数据中，异步的操作
        routeInfoService.updateRouteInfo(routeId, BaseContext.getCurrentId());
        //结束沿着路线骑行的关系
        runRouteMapper.update(routeId);
        return Result.success("结束运动成功.");
    }

    /**
     * 根据id查询路线
     * @param routeId
     * @return
     */
    public Route queryRouteById(Long routeId) {
        log.info("测试{}", routeMapper.queryById(routeId));
        return routeMapper.queryById(routeId);
    }

    /**
     * 投稿路线 修改isShare为true
     *
     * @param routeId 路线id
     * @return
     */
    public Boolean shareRoute(Long routeId) {
        Route route = routeMapper.queryById(routeId);
        route.setIsShare(true);
        routeMapper.update(route);
        return routeMapper.queryById(routeId).getIsShare() == true;
    }

    /**
     * 查询附近的路线
     *
     * @param longitude 当前用户所在位置的经度
     * @param latitude  当前用户所在位置的纬度
     * @param distance  查询的距离，单位：km，默认10km
     * @return
     */
    public List<NearRouteVo> queryNearRoute(Double longitude, Double latitude, Double distance) {

        //构造查询，附近搜索数据，条件：路线已经结束并且已经分享
        List<Route> lists = routeMapper.queryNearRoute();
        List<NearRouteVo> finallists = new ArrayList<>();
        log.info("路线数据：{}", lists);

        for (Route list : lists) {
            double curr_distance = DistanceUtils.getDistance(longitude, latitude,
                    list.getLocLongitude(),
                    list.getLocLatitude());

            if (curr_distance < distance * 1000){
                NearRouteVo routeVo = new NearRouteVo();
                routeVo.setRouteId(list.getId());
                routeVo.setTitle(list.getTitle());
                routeVo.setDistance(list.getDistance());
                routeVo.setLongitude(list.getLocLongitude());
                routeVo.setLatitude(list.getLocLatitude());
                routeVo.setUserId(list.getUserId());
                routeVo.setDate(list.getStartTime());
                routeVo.setRange(curr_distance);
                finallists.add(routeVo);
            }
        }
        log.info("附近的路线：{}", finallists);
        return finallists;
    }

    /**
     * 根据时间范围查询运动次数（包含时间边界）
     *
     * @param userId  用户id
     * @param minTime 时间范围的最小值
     * @param maxTime 时间范围的最大值
     * @return 数量
     */
    public Integer queryRouteCountByDate(Long userId, Long minTime, Long maxTime) {
        int count = routeMapper.queryRouteCountByDate();
        return count;
    }


    /**
     * 路线同行的人
     * @param routeId
     * @return
     */
    public NearUsersVo queryRouteNearUser(Long routeId) {
        List<RunRoute> lists = runRouteMapper.queryById(routeId);
        NearUsersVo nearUsersVo = new NearUsersVo();
        nearUsersVo.setCount(lists.size());
        List<NearUserVo> nearUserVoList = new ArrayList<>();
        for (RunRoute list : lists) {
            User user = userMapper.queryByUserId(list.getUserId());
            NearUserVo userVo = new NearUserVo();
            userVo.setLogo(user.getLogo());
            userVo.setUserName(user.getUsername());
            userVo.setUserId(user.getId());
            nearUserVoList.add(userVo);
        }
        nearUsersVo.setList(nearUserVoList);
        return nearUsersVo;
    }

    /**
     * 沿路线开始运动
     *
     * @param routeId 目标路线id
     * @return 新创建的路线id
     */
    public Long runFromRoute(Long routeId) {
        //创建自己的路线
        Long myRouteId = this.createRoute();
        if (null == myRouteId) {
            return null;
        }

        //保存自己的路线与目标路线之间的关系
        RunRoute runRoute = new RunRoute();
        runRoute.setRunRouteId(routeId);
        runRoute.setRouteId(myRouteId);
        runRoute.setCreated(LocalDateTime.now());
        runRoute.setUpdated(runRoute.getCreated());
        runRoute.setStatus(1);
        runRoute.setUserId(BaseContext.getCurrentId());
        runRouteMapper.save(runRoute);
        log.info("成功插入关系：{}", runRoute);
        return myRouteId;
    }

    /**
     * 查询用户的历史路线
     *
     * @param queryHistoryDTO
     * @return
     */
    public PageResult queryHistoryRoute(QueryHistoryDTO queryHistoryDTO) {
        if (BaseContext.getCurrentId() == queryHistoryDTO.getUserid()){
            PageHelper.startPage(queryHistoryDTO.getPage(), queryHistoryDTO.getPageSize());
            Page<RouteVo> page = routeMapper.OwnpageQuery(queryHistoryDTO);
            return new PageResult(page.getTotal(),page.getResult());
        }else {
            PageHelper.startPage(queryHistoryDTO.getPage(), queryHistoryDTO.getPageSize());
            Page<RouteVo> page = routeMapper.OtherpageQuery(queryHistoryDTO);
            return new PageResult(page.getTotal(),page.getResult());
        }
    }

    /**
     * 按照时间范围查询路线（包含时间边界）
     *
     * @param userId
     * @param minDate
     * @param maxDate
     * @return
     */
    public List<Route> queryRouteListByDate(Long userId, Long minDate, Long maxDate) {
        List<Route> list = routeMapper.queryRouteListByDate(userId, minDate, maxDate);
        return list;
    }

}
