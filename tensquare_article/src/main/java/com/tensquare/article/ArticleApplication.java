package com.tensquare.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.IdWorker;

/**
 * @ClassName ArticleApplication
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 0:57
 * @Version 1.0.0
*/

@SpringBootApplication
@MapperScan("com.tensquare.article.dao")
@EnableEurekaClient
@EnableFeignClients
public class ArticleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArticleApplication.class,args);
  }

  @Bean
  public IdWorker idWorker(){
    return new IdWorker(1,1);
  }
}
