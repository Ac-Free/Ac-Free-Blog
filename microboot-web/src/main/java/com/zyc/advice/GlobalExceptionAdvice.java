package com.zyc.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(Exception.class)  //可以捕获的异常类型
    @ResponseBody       //使用rest风格进行响应
    public Object exceptionHandler(Exception e){
        HashMap<String, Object> map = new HashMap<>();
        map.put("message",e.getMessage());      //获取异常信息
        map.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    //设置状态码
        map.put("exception",e.getClass().getName());    //获取异常类型
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        map.put("path",request.getRequestURI());    //异常发生的路径
        return  map;
    }
}
