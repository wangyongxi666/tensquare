package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月10日 0:00
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/user")
@Api(value = "用户接口",description = "用户管理")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{userId}")
  @ApiOperation("根据id查询用户信息")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "userId",value = "主键",required = true,dataType = "String",paramType = "path")
  })
  public Result selectById(@PathVariable String userId){
    User user = userService.selectById(userId);

    return new Result(true, StatusCode.OK,"查询成功",user);
  }

}
