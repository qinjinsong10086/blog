package com.qin.common.exception;

import com.qin.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.expr.BindingReference;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice  //异步的
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)//没有权限
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error("ShiroException  ------{}",e);
        return  Result.fail(401,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("运行时异常  ------{}",e);
        return  Result.fail(e.getMessage());
    }
    //捕获 实体类 验证异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体检验异常  ------{}",e);

        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        return  Result.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常  ------{}",e);


        return  Result.fail(e.getMessage());
    }


}
