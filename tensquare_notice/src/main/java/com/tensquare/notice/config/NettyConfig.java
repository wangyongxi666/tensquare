package com.tensquare.notice.config;

import com.tensquare.notice.netty.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName NettyConfig
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 16:25
 * @Version 1.0.0
*/
@Configuration
public class NettyConfig {

  @Bean
  public NettyServer createNettyServer(){

    NettyServer nettyServer = new NettyServer();

    //启动服务 使用新的线程启动

    new Thread(){
      @Override
      public void run() {
        nettyServer.start(1234);
      }
    }.start();

    return nettyServer;
  }

}
