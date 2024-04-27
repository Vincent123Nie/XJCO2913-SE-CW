package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.OrderService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Result<PageResult> page(OrderPageQueryDTO orderPageQueryDTO) {
        log.info("订单分页查询：{}", orderPageQueryDTO);
        PageResult pageResult = orderService.pageQuery(orderPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result completeOrCancel(@PathVariable Integer status, Long id) {
        log.info("订单完成或取消：{} {}", status, id);
        orderService.completeOrCancel(status,id);
        return  Result.success();
    }
}
