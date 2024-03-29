package com.cwk.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationPoint {
    private Long id;
    private Long route_id;
    private Double longitude; //经度
    private Double latitude; //纬度
    private Long locTime; //时间
    private Double speed; //速度，单位：千米/小时
    private int type; // 起点：1  终点：2  默认：0
}
