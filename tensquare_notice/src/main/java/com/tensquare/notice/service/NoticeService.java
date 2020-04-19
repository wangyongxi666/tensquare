package com.tensquare.notice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;

import java.util.List;
import java.util.Map;

public interface NoticeService {

  //根据id查询推送消息
  Notice selectById(String id);

  //根据条件分页查询
  Page findPage(Integer page, Integer size, Map<String,Object> map);

  //新增推送消息
  void save(Notice notice);

  //修改推送消息
  void update(Notice notice);

  //根据用户id查询未推送的消息
  Page selectNoticeFreshByUserId(String userId,Integer page,Integer size);

  //根据用户id删除待推送消息
  void deleteById(String userId);

  //测试rb事务
  void raTest();
}
