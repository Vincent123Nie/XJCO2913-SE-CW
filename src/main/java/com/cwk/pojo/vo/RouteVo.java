package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于历史路线响应数据结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteVo {

    private String id; //路线id
    private String title; //路线标题
    private Double distance; //此段轨迹的里程数，单位：米
    private Long userId; //创建路线的用户id
    private Long date; //创建路线时间戳
    private String time; //运动时间
    private Double speed; //平均速度，单位：km/h
    private Boolean isShare; //是否投稿

}
