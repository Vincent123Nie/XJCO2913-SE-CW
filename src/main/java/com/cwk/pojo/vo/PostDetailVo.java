package com.cwk.pojo.vo;

import com.cwk.pojo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailVo implements Serializable {
    private Long postId;
    private Long userId;
    private String avatar;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private List<Comment> comments;
}