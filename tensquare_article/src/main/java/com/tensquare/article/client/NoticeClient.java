package com.tensquare.article.client;

import com.tensquare.article.pojo.Notice;
import entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tensquare-notice")
@RequestMapping("/notice")
public interface NoticeClient {

  @ApiOperation("新增消息数据")
  @PostMapping
  public Result save(@RequestBody Notice notice);
}
