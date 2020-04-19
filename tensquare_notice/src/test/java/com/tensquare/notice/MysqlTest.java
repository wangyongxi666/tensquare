package com.tensquare.notice;

import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeFreshServicet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName MysqlTest
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 15:37
 * @Version 1.0.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlTest {

  @Autowired
  private NoticeFreshServicet noticeFreshServicet;

  @Test
  public void test01(){

    System.out.println(noticeFreshServicet);

    List<NoticeFresh> noticeFreshes = noticeFreshServicet.selectList(null);

    System.out.println(noticeFreshes);

    for (NoticeFresh noticeFresh : noticeFreshes) {
      noticeFresh.setNoticeId(noticeFresh.getNoticeId() + "1122334");
    }

    boolean b = noticeFreshServicet.updateBatchById(noticeFreshes, 3);

    System.out.println(b);

  }

}
