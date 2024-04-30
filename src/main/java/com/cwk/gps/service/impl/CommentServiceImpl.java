package com.cwk.gps.service.impl;

import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.CommentMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.CommentService;
import com.cwk.pojo.dto.CommentDTO;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.Comment;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());

        Page<Comment> page = commentMapper.pageQuery(pageQueryDTO);
        long total = page.getTotal();
        List<Comment> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void delete(Long id) {commentMapper.delete(id);}

    /**
     * 添加评论
     * @param commentDTO
     */
    public void insert(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setUserId(BaseContext.getCurrentId());
        comment.setPostId(commentDTO.getPostId());
        comment.setContent(commentDTO.getContent());
        commentMapper.insert(comment);
    }
}
