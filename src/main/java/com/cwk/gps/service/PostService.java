package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.dto.PostDTO;
import com.cwk.pojo.vo.PostDetailVo;
import com.cwk.pojo.vo.PostVo;

import java.util.List;

public interface PostService {

    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    void delete(Long id);

    List<PostVo> queryPosts();

    void dynamic(PostDTO postDTO);

    PostDetailVo queryPostDetail(Long postId);
}
