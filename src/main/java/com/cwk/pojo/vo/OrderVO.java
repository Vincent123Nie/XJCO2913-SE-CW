package com.cwk.pojo.vo;

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
public class OrderVO implements Serializable {

    private Long id;

    private Long userId;

    private String userName;

    //订单类型（月1、季2、年3）
    private Integer type;

    private Integer price;

    //订单状态（已取消0，待支付1，已完成2）
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
