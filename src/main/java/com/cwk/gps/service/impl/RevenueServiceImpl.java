package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.OrderMapper;
import com.cwk.gps.mapper.RevenueMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.RevenueService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.RevenuePageQueryDTO;
import com.cwk.pojo.entity.Revenue;
import com.cwk.pojo.vo.OrderVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueMapper revenueMapper;


    @Override
    public PageResult pageQuery(RevenuePageQueryDTO revenuePageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(revenuePageQueryDTO.getPage(),revenuePageQueryDTO.getPageSize());

        Page<Revenue> page = revenueMapper.pageQuery(revenuePageQueryDTO);
        long total = page.getTotal();
        List<Revenue> records = page.getResult();
        return new PageResult(total, records);
    }

}
