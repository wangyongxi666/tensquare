package com.tensquare.notice.listener;
import java.util.Date;
import java.util.Queue;

import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.management.Notification;

/**
 * @ClassName RaTransactionalTest
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月15日 12:35
 * @Version 1.0.0
*/
@Component
public class RaTransactionalTest {

  @Autowired
  private NoticeDao noticeDao;
  @Autowired
  private NoticeFreshDao noticeFreshDao;
  @Autowired
  private IdWorker idWorker;

  @RabbitListener(queues = "test_ra_Transactional")
  @Transactional
  public void raTest(String message){
    System.out.println(message);

    String id = idWorker.nextId() + "";

    Notice notice = new Notice();
    notice.setId(id);
    notice.setReceiverId("");
    notice.setOperatorId("");
    notice.setOperatorName("");
    notice.setAction("");
    notice.setTargetType("");
    notice.setTargetName("");
    notice.setTargetId("");
    notice.setCreatetime(new Date());
    notice.setType("");
    notice.setState("");
    noticeDao.insert(notice);
    System.out.println("消息记录插入成功");

    int i = 1/0;

    NoticeFresh noticeFresh = new NoticeFresh();
    noticeFresh.setUserId(id);
    noticeFresh.setNoticeId("123");


    noticeFreshDao.insert(noticeFresh);
    System.out.println("新消息插入成功");
  }

}
