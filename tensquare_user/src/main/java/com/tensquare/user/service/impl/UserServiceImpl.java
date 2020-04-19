package com.tensquare.user.service.impl;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  public User login(User user) {
    return userDao.selectOne(user);
  }

  @Override
  public User selectById(String userId) {
    User user = userDao.selectById(userId);
    return user;
  }
}
