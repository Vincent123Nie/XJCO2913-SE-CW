package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowingVo implements Serializable {
    private Long following_id;
    private String followingName;
    private String avatar;
}
