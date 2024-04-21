package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.OrderPageQueryDTO;

public interface OrderService {

    PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    void completeOrCancel(Integer status, Long id);
}
