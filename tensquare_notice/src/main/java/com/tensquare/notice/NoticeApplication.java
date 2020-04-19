package com.tensquare.notice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @ClassName NoticeApplication
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月11日 15:19
 * @Version 1.0.0
*/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class NoticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(NoticeApplication.class, args);
  }
  @Bean
  public IdWorker idWorkker(){
    return new IdWorker(1, 1);
  }


}
