package com.cwk.gps.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cwk.context.BaseContext;
import com.cwk.gps.config.BaiduConfig;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.vo.RouteQuery;
import com.cwk.pojo.vo.RunParamVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BaiduService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //驼峰转化的参数
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Autowired
    private BaiduApiService baiduApiService;

    @Autowired
    private BaiduConfig baiduConfig;


    /**
     * 创建鹰眼服务中的实体
     *
     * @param routeId 路线id
     * @return
     */
    public Boolean createEntity(Long routeId) {
        String url = this.baiduConfig.getUrl() + "/entity/add";
        return this.baiduApiService.execute(url, Method.POST,
                this.createParam(routeId), response -> {
                    if (response.isOk()) {
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     * 构建创建实体的参数
     *
     * @param routeId
     * @return
     */
    private Map<String, Object> createParam(Long routeId) {
        return MapUtil.builder("entity_name", this.createEntityName(routeId)).build();
    }

    /**
     * 创建实体名称
     *
     * @param routeId
     * @return
     */
    private Object createEntityName(Long routeId) {
        return "route_" + routeId + "_" + BaseContext.getCurrentId();
    }

    /**
     * 删除百度鹰眼服务中的实体
     *
     * @param routeId
     * @return
     */
    public boolean deleteEntity(Long routeId) {
        String url = this.baiduConfig.getUrl() + "/entity/delete";
        return this.baiduApiService.execute(url, Method.POST,
                this.createParam(routeId), response -> {
                    if (response.isOk()) {
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     * 给实体添加轨迹点
     *
     * @param routeId
     * @param runParamVo
     * @return
     */
    public Boolean uploadLocation(Long routeId, RunParamVo runParamVo) {
        String url = this.baiduConfig.getUrl() + "/track/addpoint";
        Map<String, Object> paramMap = MapUtil.builder(new HashMap<String, Object>())
                .put("entity_name", this.createEntityName(routeId))
                .put("latitude", runParamVo.getLatitude()) //纬度
                .put("longitude", runParamVo.getLongitude()) //经度
                .put("loc_time", System.currentTimeMillis() / 1000) //定位时间戳，精确到秒
                .put("coord_type_input", "bd09ll") //gcj02 坐标类型 app中使用的是腾讯地图 bd09ll百度地图
                .put("speed", runParamVo.getSpeed()).build();//速度

        return this.baiduApiService.execute(url, Method.POST,
                paramMap, response -> {
                    if (response.isOk()) {
                        String body = response.body();
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        return jsonObject.getInt("status") == 0;
                    }
                    return false;
                });
    }

    /**
     * 查询实体数据
     *
     * @param routeId   路线id
     * @param startTime 开始时间，单位：秒
     * @param endTime   结束时间，单位：秒
     */
    public RouteQuery queryEntity(Long routeId, Long startTime, Long endTime) {
        String url = this.baiduConfig.getUrl() + "/track/gettrack";
        Map<String, Object> paramMap = MapUtil.builder(new HashMap<String, Object>())
                .put("entity_name", this.createEntityName(routeId))
                .put("start_time", startTime) //开始时间
                .put("end_time", endTime) //结束时间
                .put("is_processed", 1) //是否返回纠偏后轨迹
                .put("coord_type_output", "bd09ll") //返回的坐标类型
                .build();

        return this.baiduApiService.execute(url, Method.GET, paramMap, response -> {
            if (response.isOk()) {
                String body = response.body();
                try {
                    return MAPPER.readValue(body, RouteQuery.class);
                } catch (Exception e) {
                    log.error("查询鹰眼服务出错，查询到的数据为：{}", body, e);
                }
            }
            return null;
        });

    }
}
