package com.cwk.gps.service;


import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.UserPageQueryDTO;
import com.cwk.pojo.entity.User;

public interface UserService {
    User getOne(String email);

    void save(User user);

    void update(User user);

    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    void delete(Long id);
}
