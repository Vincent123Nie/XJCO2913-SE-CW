package com.cwk.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RevenuePageQueryDTO implements Serializable {

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
