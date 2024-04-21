package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.RevenuePageQueryDTO;

public interface RevenueService {

    PageResult pageQuery(RevenuePageQueryDTO revenuePageQueryDTO);

}
