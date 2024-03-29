package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于附近的人响应数据结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearUserVo {

    private Long userId; //用户id
    private String logo; //头像
    private String userName; //昵称
}
