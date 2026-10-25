package com.al.ai.platform.core.biz.user.dto;

import com.al.ai.platform.core.biz.user.entity.AdminAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminAccountResponse extends AdminAccount {
    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
