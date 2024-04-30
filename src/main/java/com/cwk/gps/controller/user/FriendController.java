package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.FriendService;
import com.cwk.pojo.vo.FollowingVo;
import com.cwk.pojo.vo.FunsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/friend")
@Slf4j
public class FriendController {
    @Autowired
    private FriendService friendService;

    /**
     * 关注
     * @param friendId
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestParam Long friendId){
        friendService.add(friendId);
        return Result.success();
    }

    /**
     * 取关
     * @param friendId
     * @return
     */
    @DeleteMapping("/delete/{friendId}")
    public Result uncheck(@PathVariable Long friendId){
        friendService.uncheck(friendId);
        return Result.success();
    }

    /**
     * 粉丝列表
     * @return
     */
    @GetMapping("/funslist")
    public Result funsList(){
        List<FunsVo> list = friendService.queryfuns();
        return Result.success(list);
    }

    /**
     * 关注列表
     * @return
     */
    @GetMapping("/followinglist")
    public Result followingList(){
        List<FollowingVo> list = friendService.queryfollowing();
        return Result.success(list);
    }
}
