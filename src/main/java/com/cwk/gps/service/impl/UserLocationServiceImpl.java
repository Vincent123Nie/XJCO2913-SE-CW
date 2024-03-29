package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.UserLocationMapper;
import com.cwk.gps.service.UserLocationService;
import com.cwk.pojo.entity.UserLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserLocationServiceImpl implements UserLocationService {
    @Autowired
    private UserLocationMapper userLocationMapper;

    @Async //异步方法
    public CompletableFuture<String> uploadLocation(Long userId, Double longitude, Double latitude) {
        UserLocation userLocation = new UserLocation();
        userLocation.setUserId(userId);
        userLocation.setLongitude(longitude);
        userLocation.setLatitude(latitude);
        userLocation.setUpdateTime(LocalDateTime.now());
        UserLocation userLocation1 = userLocationMapper.queryById(userId);
        if (userLocation1 == null){
            userLocationMapper.insert(userLocation);
        }else{
            userLocationMapper.update(userLocation);
        }
        return CompletableFuture.completedFuture("ok");
    }
}

