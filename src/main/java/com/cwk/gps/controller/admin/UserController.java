package com.cwk.gps.controller.admin;

import com.cwk.gps.annotation.Log;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.UserService;
import com.cwk.pojo.dto.UserPageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminUserController")
@RequestMapping("/admin/user")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO) {
        log.info("用户分页查询：{}", userPageQueryDTO);
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Log(method = "Delete user")
    public Result delete(Long id){
        log.info("删除用户：{}", id);
        userService.delete(id);
        return Result.success();
    }
}
