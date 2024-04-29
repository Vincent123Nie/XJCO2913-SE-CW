package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.CommentMapper;
import com.cwk.gps.mapper.LogMapper;
import com.cwk.gps.mapper.PostMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.LogService;
import com.cwk.gps.service.PostService;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Post;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
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
}
