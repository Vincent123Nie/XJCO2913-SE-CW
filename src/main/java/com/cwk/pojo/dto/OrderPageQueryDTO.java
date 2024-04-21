package com.cwk.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPageQueryDTO implements Serializable {

    //下单用户的名字
    private String userName;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
