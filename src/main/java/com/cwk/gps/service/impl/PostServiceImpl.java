package com.cwk.gps.service.impl;

import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.CommentMapper;
import com.cwk.gps.mapper.LogMapper;
import com.cwk.gps.mapper.PostMapper;
import com.cwk.gps.mapper.UserMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.LogService;
import com.cwk.gps.service.PostService;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.dto.PostDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Comment;
import com.cwk.pojo.entity.Post;
import com.cwk.pojo.vo.PostDetailVo;
import com.cwk.pojo.vo.PostVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;



    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());

        Page<Post> page = postMapper.pageQuery(pageQueryDTO);
        long total = page.getTotal();
        List<Post> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void delete(Long id) {

        //删除帖子相关联的评论
        commentMapper.deleteByPostId(id);

        //删除帖子
        postMapper.delete(id);
    }


    /**
     * 发帖子
     * @param postDTO
     */
    public void dynamic(PostDTO postDTO) {
        Post post = new Post();
        post.setUserId(BaseContext.getCurrentId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postMapper.insert(post);
    }

    /**
     * 查询帖子
     * @return
     */
    public List<PostVo> queryPosts() {
        List<Post> list = postMapper.query();
        List<PostVo> targetList = new ArrayList<>();
        for (Post post : list) {
            PostVo postVo = new PostVo();
            postVo.setPostId(post.getPostId());
            postVo.setUserId(post.getUserId());
            postVo.setTitle(post.getTitle());
            postVo.setContent(post.getContent());
            postVo.setCreateTime(post.getCreateTime());
            postVo.setAvatar(userMapper.queryAvatarByUserId(post.getUserId()));
            targetList.add(postVo);
        }
        return targetList;
    }

    /**
     * 查询帖子的细节
     * @param postId
     * @return
     */
    public PostDetailVo queryPostDetail(Long postId) {
        PostDetailVo postDetailVo = new PostDetailVo();
        Post post = postMapper.queryByPostId(postId);
        postDetailVo.setPostId(post.getPostId());
        postDetailVo.setUserId(post.getUserId());
        postDetailVo.setTitle(post.getTitle());
        postDetailVo.setContent(post.getContent());
        postDetailVo.setCreateTime(post.getCreateTime());
        postDetailVo.setAvatar(userMapper.queryAvatarByUserId(post.getUserId()));
        List<Comment> list = commentMapper.queryByPostID(postId);
        log.info("list:{}",list);
        postDetailVo.setComments(list);
        return postDetailVo;
    }
}
