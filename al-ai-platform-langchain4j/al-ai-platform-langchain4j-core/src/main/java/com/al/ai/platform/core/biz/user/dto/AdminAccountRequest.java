package com.al.ai.platform.core.biz.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminAccountRequest extends AdminAccountResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
