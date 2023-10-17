package hu.helper.bang.center.start.advise;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author : Luo Siwei
 * @Date : 2023/3/9 21:01
 * @Description : 控制器全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class BangControllerAdvice {
    @ExceptionHandler(BangException.class)
    @ResponseBody
    public BangResult handleBangException(BangException e) {
        log.error("接口请求出错，错误信息", e);
        return BangResult.error(e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BangResult handleBangException(BindException e) {
        log.error("接口请求出错，错误信息", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return BangResult.error(collect);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BangResult handleBangException(ConstraintViolationException e) {
        log.error("接口请求出错，错误信息", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String collect = constraintViolations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return BangResult.error(collect);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BangResult handleBangException(MissingServletRequestParameterException e) {
        log.error("接口请求出错，错误信息", e);
        return BangResult.error(String.format("%s字段不能未空", e.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BangResult handleBangException(MethodArgumentNotValidException e) {
        log.error("接口请求出错，错误信息", e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField()).append(error.getDefaultMessage()).append(";");
        }
        return BangResult.error(sb.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BangResult handleBangException(Exception e) {
        log.error("接口请求出错，错误信息", e);
        return BangResult.error(e.getMessage());
    }
}
