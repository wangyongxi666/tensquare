package com.tensquare.article.repository;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String>{

  //根据文章评论数据
  List<Comment> findByArticleid(String articleid);

  //根据用户id查询评论 并进行排序
  List<Comment> findByUseridOrderByPublishdateDesc(String userid);

}
