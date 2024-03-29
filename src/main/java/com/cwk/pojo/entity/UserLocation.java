package com.cwk.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 存储用户地理位置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    private Long id;
    private Long userId; //用户id
    private Double longitude; //经度
    private Double latitude; //纬度
    private LocalDateTime updateTime; //更新时间

}
