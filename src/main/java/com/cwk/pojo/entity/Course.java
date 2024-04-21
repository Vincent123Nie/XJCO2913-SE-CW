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
public class Course implements Serializable {

    private Long id;

    private String name;

    private String picture;

    private String resource;

    //Weight-losing Muscle-building Rehabilitation Yoga Dancing
    private String type;

    private Integer duration;

    private Integer calorie;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
