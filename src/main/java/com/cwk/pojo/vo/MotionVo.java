package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotionVo {
    //结束时间 yyyy-MM-dd
    private String date;
    //运动花费时间
    private String time;
    //运动消耗的卡路里
    private Double carlorie;
}
