package com.tensquare.article.service;

import com.tensquare.article.pojo.Comment;

import java.util.List;

public interface CommentService {

  //查询所有评论
  List<Comment> findAll();

  //根据id查询评论
  Comment findById(String commentId);

  //新增评论
  void inster(Comment comment);

  //id存在则修改 不存在则新增
  void update(Comment comment);

  //根据id删除
  void deleteById(String commentId);

  //根据文章id查询评论
  List<Comment> findByArticleId(String articleId);

  //点赞数增加
  void updateThumbupByCommentId(String commentId);

}
