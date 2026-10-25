package com.al.ai.platform.admin.exception;

import com.al.ai.platform.core.common.exception.BusinessException;
import com.al.ai.platform.core.common.result.Result;
import com.al.ai.platform.core.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class, BindException.class})
    public Result<Void> handleBadRequest(Exception e) {
        return Result.error(ResultCode.PARAMS_VALIDATION_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleOther(Exception e) {
        log.error("unhandled error", e);
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
}
