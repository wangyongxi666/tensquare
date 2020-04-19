package com.tensquare.notice.config;

import com.tensquare.notice.listener.SysNoticeListener;
import com.tensquare.notice.listener.UserNoticeListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitmqConfit
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 21:53
 * @Version 1.0.0
*/
@Configuration
public class RabbitConfig {

  @Bean("sysNoticeContainer")
  public SimpleMessageListenerContainer create(ConnectionFactory connectionFactory){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

    //使用channel监听
    container.setExposeListenerChannel(true);

    //设置自己编写的监听器
    container.setMessageListener(new SysNoticeListener());

    return container;
  }

  @Bean("userNoticeContainer")
  public SimpleMessageListenerContainer userCreate(ConnectionFactory connectionFactory){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

    //使用channel监听
    container.setExposeListenerChannel(true);

    //设置自己编写的监听器
    container.setMessageListener(new UserNoticeListener());

    return container;
  }

  //测试ra队列 监听类事务--------------------------------------------------
  public static final String TEST_RA_TRANSACTIONAL = "test_ra_Transactional";
  //定义队列
  @Bean(TEST_RA_TRANSACTIONAL)
  public static Queue create(){
    return new Queue(TEST_RA_TRANSACTIONAL);
  }

}
