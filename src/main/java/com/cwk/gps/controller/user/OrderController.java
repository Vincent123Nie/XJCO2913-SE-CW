package com.cwk.gps.controller.user;


import com.cwk.gps.result.Result;
import com.cwk.gps.service.OrderService;
import com.cwk.pojo.dto.OrdersSubmitDTO;
import com.cwk.pojo.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    public Result<Long> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("用户下单，参数为：{}", ordersSubmitDTO);
        Long orderNo = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderNo);
    }
}
