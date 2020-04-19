package com.tensquare.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName BaseExceptionHandler
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 13:40
 * @Version 1.0.0
*/
@ControllerAdvice
public class BaseExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Result hander(Exception e){
    System.out.println("异常信息为：" + e.getMessage());

    return new Result(false, StatusCode.ERROR,e.getMessage());
  }

}
