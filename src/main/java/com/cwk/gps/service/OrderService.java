package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.OrdersSubmitDTO;

public interface OrderService {

    PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    void completeOrCancel(Integer status, Long id);

    Long submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
