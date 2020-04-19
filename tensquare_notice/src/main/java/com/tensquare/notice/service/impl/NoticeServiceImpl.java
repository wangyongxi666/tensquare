package com.tensquare.notice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.client.ArticleClient;
import com.tensquare.notice.client.UserClient;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import entity.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.*;

/**
 * @ClassName NoticeServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月11日 15:54
 * @Version 1.0.0
*/
@Service
public class NoticeServiceImpl implements NoticeService {

  @Autowired
  private NoticeDao noticeDao;

  @Autowired
  private IdWorker idWorker;

  @Autowired
  private NoticeFreshDao noticeFreshDao;

  @Autowired
  private UserClient userClient;

  @Autowired
  private ArticleClient articleClient;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public Notice selectById(String id) {

    Notice notice = noticeDao.selectById(id);

    //完善 用户名和文章名 字段值
    this.getInfo(notice);

    return notice;
  }

  @Override
  public Page findPage(Integer page, Integer size, Map<String,Object> map) {

    //封装分页参数
    Page pageParam = new Page(page,size);

    //封装条件参数
    EntityWrapper entityWrapper = new EntityWrapper();
    Set keySet = map.keySet();

    if(keySet != null && keySet.size() > 0){
      for (Object key : keySet) {

        String keyStr = (String) key;

        entityWrapper.eq(map.get(keyStr) != null,keyStr,map.get(keyStr));
      }
    }

    List<Notice> list = noticeDao.selectPage(pageParam, entityWrapper);

    //完善 用户名和文章名 字段值
    for (Notice notice : list) {
      this.getInfo(notice);
    }

    pageParam.setRecords(list);

    return pageParam;
  }

  @Override
  public void save(Notice notice) {
    //设置初始值
    //设置状态 0未读 1已读
    notice.setState("0");
    notice.setCreatetime(new Date());

    //设置id
    String id = idWorker.nextId() + "";
    notice.setId(id);

    //待推送消息入库，新消息提醒
//    NoticeFresh noticeFresh = new NoticeFresh();
//    noticeFresh.setNoticeId(id);
//    noticeFresh.setUserId(notice.getReceiverId());
//
//    noticeFreshDao.insert(noticeFresh);

    noticeDao.insert(notice);
  }

  @Override
  public void update(Notice notice) {
    noticeDao.updateById(notice);
  }

  @Override
  public Page selectNoticeFreshByUserId(String userId, Integer page, Integer size) {

    //设置分页参数
    Page pageParam = new Page(page,size);

    //设置查询条件
    EntityWrapper wrapper = new EntityWrapper();
    wrapper.eq("userId",userId);

    //执行查询
    List<NoticeFresh> list = noticeFreshDao.selectPage(pageParam,wrapper);

    //封装返回数据
    pageParam.setRecords(list);

    return pageParam;
  }

  @Override
  public void deleteById(String userId) {

    noticeFreshDao.deleteById(userId);

  }

  //补全消息中的 用户昵称 操作对象的名称
  private void getInfo(Notice notice){

    //查询用户信息
    String receiverId = notice.getReceiverId();
    Result userResult = userClient.selectById(receiverId);
    HashMap userMap = (HashMap) userResult.getData();
    String nickname = (String) userMap.get("nickname");
    notice.setOperatorName(nickname);

    //查询文章信息
    String targetId = notice.getTargetId();
    Result articleResult = articleClient.findById(targetId);
    HashMap articleMap = (HashMap) articleResult.getData();
    String title = (String) articleMap.get("title");
    notice.setTargetName(title);
  }

  //测试rb事务
  @Override
  public void raTest() {
    rabbitTemplate.convertAndSend("test_ra_Transactional",1);
  }
}
