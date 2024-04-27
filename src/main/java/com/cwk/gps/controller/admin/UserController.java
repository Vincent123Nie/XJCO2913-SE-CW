package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.UserService;
import com.cwk.pojo.dto.UserPageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
