package com.cwk.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author
 * @Date Created in  2024/1/13 15:26
 * @DESCRIPTION: alipay接口参数
 * @Version V1.0
 */
@Data
public class AliPay {
    //订单编号
    private String out_trade_no;
    //商品金额
    private double total_amount;
    //商品名称
    private String subject;
}