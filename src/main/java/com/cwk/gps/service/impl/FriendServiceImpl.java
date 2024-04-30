package com.cwk.gps.service.impl;

import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.FriendMapper;
import com.cwk.gps.mapper.UserMapper;
import com.cwk.gps.service.FriendService;
import com.cwk.pojo.entity.FriendShip;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.FollowingVo;
import com.cwk.pojo.vo.FunsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 关注
     * @param friendId
     */
    public void add(Long friendId) {
        FriendShip friendShip = new FriendShip();
        friendShip.setFriendId(friendId);
        friendShip.setUserId(BaseContext.getCurrentId());
        friendShip.setCreateTime(LocalDateTime.now());
        friendMapper.insert(friendShip);
    }

    /**
     * 取关
     * @param friendId
     */
    public void uncheck(Long friendId) {
        friendMapper.delete(friendId, BaseContext.getCurrentId());
    }

    /**
     * 查询粉丝列表
     * @return
     */
    public List<FunsVo> queryfuns() {
        List<Long> ids = friendMapper.queryfuns(BaseContext.getCurrentId());
        log.info("粉丝id:{}",ids);
        List<FunsVo> list = new ArrayList<>();
        if (ids.size() > 0){
            for (Long id : ids) {
                User user = userMapper.queryByUserId(id);
                FunsVo funsVo = new FunsVo();
                funsVo.setFuns_id(id);
                funsVo.setFansName(user.getUsername());
                funsVo.setAvatar(user.getAvatar());
                list.add(funsVo);
            }
        }
        return list;
    }

    /**
     * 关注列表
     * @return
     */
    public List<FollowingVo> queryfollowing() {
        List<Long> ids = friendMapper.following(BaseContext.getCurrentId());
        log.info("关注博主id:{}",ids);
        List<FollowingVo> list = new ArrayList<>();
        if (ids.size() > 0){
            for (Long id : ids) {
                User user = userMapper.queryByUserId(id);
                FollowingVo followingVo = new FollowingVo();
                followingVo.setFollowing_id(id);
                followingVo.setFollowingName(user.getUsername());
                followingVo.setAvatar(user.getAvatar());
                list.add(followingVo);
            }
        }
        return list;
    }


}
