package com.tensquare.user.service;

import com.tensquare.user.pojo.User;

public interface UserService {

  User login(User user);

  //根据用户id查询对象
  User selectById(String userId);
}
