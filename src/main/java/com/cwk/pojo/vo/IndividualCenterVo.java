package com.cwk.pojo.vo;

import com.cwk.pojo.entity.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndividualCenterVo {

    private Long id;
    private String username; //昵称
    private String avatar; //头像
    private int followingcount;//关注数
    private int funscount;//粉丝数
    private int postscount;//帖子数
}
