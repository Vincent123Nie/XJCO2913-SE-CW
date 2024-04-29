package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.PageQueryDTO;

public interface CommentService {

    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    void delete(Long id);

}
