package com.al.ai.platform.core.service;

import com.al.ai.platform.core.common.dto.PageResult;

import java.util.List;

public interface BaseService<REQ, RES> {
    PageResult<RES> pageQuery(REQ request);
    List<RES> list(REQ request);
    RES getById(Long id);
    int create(REQ request);
    int update(REQ request);
    int deleteById(Long id);
}
