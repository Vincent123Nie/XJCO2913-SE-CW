package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.FriendShipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/friend")
@Slf4j
public class FriendShipController {
    @Autowired
    private FriendShipService friendShipService;

    @PostMapping("/add")
    public Result addFriend(Long userId, Long friendId){
        friendShipService.addfriend(userId, friendId);
        return Result.success();
    }
}
