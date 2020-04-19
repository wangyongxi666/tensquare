package com.tensquare.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.client.NoticeClient;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.pojo.Notice;
import com.tensquare.article.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ArticleServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 1:25
 * @Version 1.0.0
*/
@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private IdWorker idWorker;

  @Autowired
  private ArticleDao articleDao;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private NoticeClient noticeClient;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public List<Article> findAll() {

    return articleDao.selectList(null);
  }

  @Override
  public Article findById(String articleId) {
    return articleDao.selectById(articleId);
  }

  @Override
  public void add(Article article) {
    // TODO: 2020/4/12 使用jwt鉴权，获取当前用户的登陆信息，用户id（文章作者id）

    long id = idWorker.nextId();
    article.setId(String.valueOf(id));

    articleDao.insert(article);

    //获取订阅者集合
    String authorKey = "article_author_" + article.getUserid();
    Set<String> set = redisTemplate.boundSetOps(authorKey).members();

    //给订阅者创建消息通知
    for (String uid : set) {
      //创建消息对象
      Notice notice =  new Notice();
      notice.setReceiverId(uid);
      notice.setOperatorId(article.getUserid());
      notice.setAction("publish");
      notice.setTargetType("article");
      notice.setTargetId(String.valueOf(id));
      notice.setCreatetime(new Date());
      notice.setType("sys");
      notice.setState("0");

      noticeClient.save(notice);
    }

    //创建交换
    String exchangeName = "article_subscribe";
    //路由key(作者id)
    String articleUserId = article.getUserid();
    //新增文章后 创建消息，通知给订阅者
    rabbitTemplate.convertAndSend(exchangeName,articleUserId,id);

  }

  @Override
  public void updateById(Article article) {
    //根据id修改
    articleDao.updateById(article);

    //    //根据条件修改
//    EntityWrapper wrapper = new EntityWrapper();
//    wrapper.eq("id",article.getId());
//    articleDao.update(article,wrapper);
  }

  @Override
  public void deleteById(String articleId) {
    articleDao.deleteById(articleId);
  }

  @Override
  public Page findPage(Integer page, Integer size, Map<String, Object> map) {

    //设置查询对象
    EntityWrapper wrapper = new EntityWrapper();

    //有设置参数就设置查询条件
    if(map != null){
      Set<String> keys = map.keySet();
      for (String key : keys) {
        wrapper.eq(map.get(key) != null,key,map.get(key));
      }
    }

    //设置分页参数
    Page pageable = new Page(page,size);

    //执行查询
    List list = articleDao.selectPage(pageable, wrapper);

    pageable.setRecords(list);

    //返回
    return pageable;
  }

  @Override
  public Boolean subscribe(String articleId, String userId) {

    //根据文章id查询文章作者id
    Article article = articleDao.selectById(articleId);
    String articleUserId = article.getUserid();

    //Rabbitmq
    //创建rabbit管理器
    RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
    //创建交换机
    String exchangeName = "article_subscribe";
    DirectExchange directExchange = new DirectExchange(exchangeName);
    rabbitAdmin.declareExchange(directExchange);
    //创建队列
    String queueKey = exchangeName + "_" + userId;
    Queue queue = new Queue(queueKey,true);
    //绑定交换机和队列和路由key
    Binding binding = BindingBuilder.bind(queue).to(directExchange).with(articleUserId);//作者id


    //Redis
    //key
    //存放着用户订阅信息的集合，里面存放着作者id
    String userKey = "article_subscribe_" + userId;
    //存放着作者粉丝的集合，里面存放着普通用户id
    String authorKey = "article_author_" + articleUserId;
    //根据文章作者id和订阅者id查询数据，有数据则已经订阅过，现在取消； 没有数据则是没订阅过，现在是订阅
    Boolean flag = redisTemplate.boundSetOps(userKey).isMember(articleUserId);

    if (flag) {
      //之前有关系，现在取消
      redisTemplate.opsForSet().remove(userKey,articleUserId);
      redisTemplate.opsForSet().remove(authorKey,userId);

      //删除绑定关系
      rabbitAdmin.removeBinding(binding);

      return false;
    }else{
      //之前没关系，现在订阅
      redisTemplate.boundSetOps(userKey).add(articleUserId);
      redisTemplate.boundSetOps(authorKey).add(userId);

      //使用创建的队列
      rabbitAdmin.declareQueue(queue);
      //添加绑定关系
      rabbitAdmin.declareBinding(binding);

      return true;
    }
  }

  @Override
  public void thumbupForArticle(String articleId, String userId) {

    //查询出文章数据
    Article article = articleDao.selectById(articleId);
    article.setUpdatetime(new Date());
    article.setThumbup(article.getThumbup() + 1);

    articleDao.updateById(article);

    //点赞成功后 需要发送消息给文章作者
    Notice notice = new Notice();
    notice.setReceiverId(article.getUserid());
    notice.setOperatorId(userId);
    notice.setAction("thumbup");
    notice.setTargetType("article");
    notice.setTargetId(articleId);
    notice.setCreatetime(new Date());
    notice.setType("user");
    notice.setState("0");

    noticeClient.save(notice);

    //Rabbitmq
    //创建rabbit管理器
    RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
    //创建队列 每个作者都有自己的id 通过作者id区分
    String queueKey = "article_thumbup_" + article.getUserid();
    Queue queue = new Queue(queueKey,true);

    //声明使用队列
    rabbitAdmin.declareQueue(queue);

    //发送消息到队列中
    rabbitTemplate.convertAndSend(queueKey,articleId);
  }
}
