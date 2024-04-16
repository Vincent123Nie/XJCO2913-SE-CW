package com.cwk.gps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.UserMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.UserService;
//import com.cwk.pojo.dto.UserPageQueryDTO;
import com.cwk.pojo.entity.Employee;
import com.cwk.pojo.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getOne(String email) {
        return userMapper.getOne(email);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public void update(User user) {
        if (user.getId() == null){
            user.setId(BaseContext.getCurrentId());
        }
        userMapper.update(user);
    }

//    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
//        //开始分页查询
//        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
//
//        Page<Employee> page = userMapper.pageQuery(userPageQueryDTO);
//        long total = page.getTotal();
//        List<Employee> records = page.getResult();
//        return new PageResult(total, records);
//    }
}
