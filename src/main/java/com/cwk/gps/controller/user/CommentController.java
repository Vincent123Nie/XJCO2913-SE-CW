package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.CommentService;
import com.cwk.gps.service.PostService;
import com.cwk.pojo.dto.CommentDTO;
import com.cwk.pojo.dto.PostDTO;
import com.cwk.pojo.entity.Comment;
import com.cwk.pojo.vo.PostDetailVo;
import com.cwk.pojo.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param commentDTO
     * @return
     */
    @PostMapping("/add")
    public Result leaveMessage(@RequestBody CommentDTO commentDTO){
        commentService.insert(commentDTO);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public Result deleteMessage(@PathVariable Long commentId){
        commentService.delete(commentId);
        return Result.success();
    }

}
