package com.cwk.gps.controller.admin;

import com.cwk.gps.annotation.Log;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.CommentService;
import com.cwk.pojo.dto.PageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminCommentController")
@RequestMapping("/admin/comment")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        log.info("评论分页查询：{}", pageQueryDTO);
        PageResult pageResult = commentService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Log(method = "Delete comment")
    public Result delete(Long id){
        log.info("删除评论：{}", id);
        commentService.delete(id);
        return Result.success();
    }
}
