package com.cwk.pojo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 沿着路线运动
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunRoute {

    private Long id; //主键id
    private Long runRouteId; //沿着跑的路线id
    private Long routeId; //自己的路线id
    private Long userId; //自己的id
    private Integer status; //状态，1-运动中，0-结束
    private LocalDateTime created; //创建时间
    private LocalDateTime updated; //更新时间
}
