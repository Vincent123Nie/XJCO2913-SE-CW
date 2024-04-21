package com.cwk.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    //月卡
    public static final Integer MONTH = 0;
    //季卡
    public static final Integer SEASON = 1;
    //年卡
    public static final Integer YEAR = 2;

    //取消
    public static final Integer CANCEL = 0;
    //待支付
    public static final Integer WAITING = 1;
    //已完成
    public static final Integer COMPLETE = 2;


    private Long id;

    private Long userId;

    //订单类型（月1、季2、年3）
    private Integer type;

    private Integer price;

    //订单状态（已取消0，待支付1，已完成2）
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
