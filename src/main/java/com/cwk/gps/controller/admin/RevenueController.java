package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.OrderService;
import com.cwk.gps.service.RevenueService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.RevenuePageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/revenue")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/page")
    public Result<PageResult> page(RevenuePageQueryDTO revenuePageQueryDTO) {
        log.info("收入分页查询：{}", revenuePageQueryDTO);
        PageResult pageResult = revenueService.pageQuery(revenuePageQueryDTO);
        return Result.success(pageResult);
    }
}
