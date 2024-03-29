package com.cwk.pojo.vo;

import com.cwk.pojo.entity.LocationPoint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteQuery {
    private Long id; //主键id
    private String title; //路线标题
    private Long userId; //创建路线的用户id
    private Boolean isShare; //是否投稿
    private Integer size; //轨迹点数量
    private Double distance; //此段轨迹的里程数，单位：米
    private String time; //运动时间，格式：mm:ss
    private Double speed; //平均速度，单位：km/h
    private Long startTime; //创建路线时间戳
    private Long endTime; //结束路线时间戳
    private LocationPoint startPoint; //起点信息
    private LocationPoint endPoint; //终点信息
    private List<LocationPoint> points; //历史轨迹点列表
    private Double loc_latitude;
    private Double loc_longitude;
    private Integer status; //状态，1：开始，0：已结束
}
