package com.tensquare.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tensquare-article")
@RequestMapping("/article")
public interface ArticleClient {

  //根据文章id获取文章数据
  @GetMapping("/{articleId}")
  Result findById(@PathVariable("articleId") String articleId);

}
