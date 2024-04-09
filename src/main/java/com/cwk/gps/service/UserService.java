package com.cwk.gps.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwk.pojo.entity.User;

public interface UserService {
    User getOne(String email);

    void save(User user);

    void update(User user);
}
