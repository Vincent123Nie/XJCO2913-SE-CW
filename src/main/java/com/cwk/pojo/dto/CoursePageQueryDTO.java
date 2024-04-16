package com.cwk.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePageQueryDTO implements Serializable {

    //课程名
    private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
