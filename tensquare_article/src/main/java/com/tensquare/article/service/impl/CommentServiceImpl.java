package com.tensquare.article.service.impl;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.repository.CommentRepository;
import com.tensquare.article.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CommentServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 23:24
 * @Version 1.0.0
*/
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private IdWorker idWorker;

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Comment> findAll() {
    return commentRepository.findAll();
  }

  @Override
  public Comment findById(String commentId) {

    Optional<Comment> opt = commentRepository.findById(commentId);

    if(opt.isPresent()){
      return opt.get();
    }

    return null;
  }

  @Override
  public void inster(Comment comment) {
    //生成id
    comment.set_id(String.valueOf(idWorker.nextId()));
    //初始化数据
    comment.setPublishdate(new Date());
    comment.setThumbup(0);

    commentRepository.insert(comment);
  }

  @Override
  public void update(Comment comment) {

    commentRepository.save(comment);

  }

  @Override
  public void deleteById(String commentId) {
    commentRepository.deleteById(commentId);
  }

  @Override
  public List<Comment> findByArticleId(String articleId) {

    List<Comment> list = commentRepository.findByArticleid(articleId);
    return list;
  }

  @Override
  public void updateThumbupByCommentId(String commentId) {

    Query query = new Query();
    Update update = new Update();

    query.addCriteria(Criteria.where("_id").is(commentId));
    update.inc("thumbup",1);

    mongoTemplate.updateFirst(query,update,"comment");
  }

}
