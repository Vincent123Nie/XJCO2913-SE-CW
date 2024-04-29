package com.cwk.gps.controller.admin;

import com.cwk.gps.annotation.Log;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.PostService;
import com.cwk.pojo.dto.PageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminPostController")
@RequestMapping("/admin/post")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        log.info("帖子分页查询：{}", pageQueryDTO);
        PageResult pageResult = postService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Log(method = "Delete post")
    public Result delete(Long id){
        log.info("删除帖子：{}", id);
        postService.delete(id);
        return Result.success();
    }
}
