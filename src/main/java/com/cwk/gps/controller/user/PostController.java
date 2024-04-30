package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.PostService;
import com.cwk.pojo.dto.PostDTO;
import com.cwk.pojo.entity.Post;
import com.cwk.pojo.vo.PostDetailVo;
import com.cwk.pojo.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/post")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 发帖子
     * @param postDTO
     * @return
     */
    @PostMapping("/article")
    public Result dynamic(@RequestBody PostDTO postDTO){
        log.info("内容：{}",postDTO);
        postService.dynamic(postDTO);
        return Result.success();
    }

    /**
     * 查询所有帖子
     * @return
     */
    @GetMapping
    public Result<List<PostVo>> queryPosts(){
        List<PostVo> list = postService.queryPosts();
        return Result.success(list);
    }

    /**
     * 查询帖子的细节
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public Result<PostDetailVo> queryPostDetail(@PathVariable Long postId){
        PostDetailVo postDetailVo = postService.queryPostDetail(postId);
        return Result.success(postDetailVo);
    }

    /**
     * 删除帖子
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        postService.delete(id);
        return Result.success();
    }
}
