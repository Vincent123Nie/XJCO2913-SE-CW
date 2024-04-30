package com.cwk.gps.service;

import com.cwk.pojo.vo.FollowingVo;
import com.cwk.pojo.vo.FunsVo;

import java.util.List;

public interface FriendService {
    void add(Long friendId);

    void uncheck(Long friendId);


    List<FunsVo> queryfuns();

    List<FollowingVo> queryfollowing();
}
