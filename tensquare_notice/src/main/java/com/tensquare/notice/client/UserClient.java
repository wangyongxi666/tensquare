package com.tensquare.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tensquare-user")
@RequestMapping("/user")
public interface UserClient {

  @GetMapping("/{userId}")
  Result selectById(@PathVariable("userId") String userId);

}
