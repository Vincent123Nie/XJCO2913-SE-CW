package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.LogService;
import com.cwk.pojo.dto.PageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/log")
@CrossOrigin(origins = "http://localhost:8081")
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        log.info("日志分页查询：{}", pageQueryDTO);
        PageResult pageResult = logService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }
}
