package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.PageQueryDTO;

public interface MemberService {

    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    void cancel(Long id);
}
