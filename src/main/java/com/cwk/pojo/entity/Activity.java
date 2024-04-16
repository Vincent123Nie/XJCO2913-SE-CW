package com.cwk.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    /**
     * 活动状态 0未开始 1进行中 2已结束
     */
    public static final Integer UNSTARTED = 0;
    public static final Integer ONGOING = 1;
    public static final Integer FINISHED = 2;

    private Long id;

    private String name;

    private String picture;

    private Integer actualParticipant;

    private Integer maxParticipant;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
