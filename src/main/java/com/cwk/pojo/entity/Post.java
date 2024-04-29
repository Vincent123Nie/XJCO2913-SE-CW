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
public class Post implements Serializable {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}