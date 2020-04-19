package com.tensquare.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName EncryptApplication
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月10日 17:57
 * @Version 1.0.0
*/
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EncryptApplication {

  public static void main(String[] args) {
    SpringApplication.run(EncryptApplication.class);
  }


}
