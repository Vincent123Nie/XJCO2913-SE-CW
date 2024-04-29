package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.LogMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.LogService;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());

        Page<AdminLog> page = logMapper.pageQuery(pageQueryDTO);
        long total = page.getTotal();
        List<AdminLog> records = page.getResult();
        return new PageResult(total, records);
    }
}
