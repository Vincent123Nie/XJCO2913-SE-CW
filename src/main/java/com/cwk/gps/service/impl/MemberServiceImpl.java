package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.MemberMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.MemberService;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.Member;
import com.cwk.pojo.vo.OrderVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());

        Page<Member> page = memberMapper.pageQuery(pageQueryDTO);
        long total = page.getTotal();
        List<Member> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void cancel(Long id) {
        memberMapper.cancel(id);
    }
}
