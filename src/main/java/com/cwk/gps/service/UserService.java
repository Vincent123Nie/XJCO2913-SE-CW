package com.cwk.gps.service;


import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.UserPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Post;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.BloggerInfoVo;
import com.cwk.pojo.vo.IndividualCenterVo;

import java.util.List;

public interface UserService {
    User getOne(String email);

    void save(User user);

    void update(User user);

    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    void delete(Long id);

    BloggerInfoVo queryOther(Long id);

    IndividualCenterVo queryCenter();

    List<Post> queryMyPosts();

    List<Activity> queryMyActivity();
}
