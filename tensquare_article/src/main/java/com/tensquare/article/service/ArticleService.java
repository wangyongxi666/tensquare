package com.tensquare.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

  //查询文章全部列表
  List<Article> findAll();

  //根据id查询文章
  Article findById(String articleId);

  //添加文章
  void add(Article article);

  //修改文章
  void updateById(Article article);

  //根据id删除
  void deleteById(String articleId);

  //分页条件查询
  Page findPage(Integer page, Integer size, Map<String, Object> map);

  //根据文章id和用户id，建立了订阅关系 保存文章作者id和订阅者的id的关系
  Boolean subscribe(String articleId, String userId);

  //用户点赞文章
  void thumbupForArticle(String articleId, String userId);
}
