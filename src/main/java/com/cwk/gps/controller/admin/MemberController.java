package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.MemberService;
import com.cwk.gps.service.OrderService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.PageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminMemberController")
@RequestMapping("/admin/member")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/page")
    public Result<PageResult> page(PageQueryDTO pageQueryDTO) {
        log.info("会员分页查询：{}", pageQueryDTO);
        PageResult pageResult = memberService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result cancel(Long id) {
        log.info("取消会员：{}", id);
        memberService.cancel(id);
        return  Result.success();
    }
}
