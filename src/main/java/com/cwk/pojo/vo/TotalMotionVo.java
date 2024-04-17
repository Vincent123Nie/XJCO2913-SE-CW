package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalMotionVo {
    private Double totalDistance; //总里程
    private String totalTime; //累计时间
    private List<MotionVo> list = new ArrayList<>();
}
