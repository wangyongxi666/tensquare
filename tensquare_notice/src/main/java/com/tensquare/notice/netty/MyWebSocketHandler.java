package com.tensquare.notice.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensquare.notice.config.ApplicationContextProvider;
import entity.Result;
import entity.StatusCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MyWebSocketHandler
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 16:26
 * @Version 1.0.0
*/
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  private static ObjectMapper mapper = new ObjectMapper();

  //存放webSocket连接map，根据用户id存放
  public static ConcurrentHashMap<String,Channel> userChannelMap = new ConcurrentHashMap();

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {

    //第一次请求的时候，需要建立webSocket连接
    //约定用户第一次请求携带的数据 ： {"userId":"1"}
    String json = msg.text();
    //解析json数据 获取内容对象
    String userId = mapper.readTree(json).get("userId").asText();

    //判断是否第一次连接
    Channel channel = userChannelMap.get(userId);
    if(channel == null){
      //建立webSocket连接
      channel = channelHandlerContext.channel();

      //把连接放到容器中
      userChannelMap.put(userId,channel);
    }

    //完成新消息提醒即可，只需要获取消息的数量 （文章发布通知用户---------------------------------）
    //从容器中获取rabbitmqTemplate对象
    RabbitTemplate rabbitTemplate = ApplicationContextProvider.getApplicationContext().getBean(RabbitTemplate.class);
    //获取rabbitmq的消息，并发送给用户
    RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

    //获取新消息的数量
    String queueName = "article_subscribe_" + userId;
    Properties queueProperties = rabbitAdmin.getQueueProperties(queueName);

    Integer count = 0;
    if(queueProperties != null){
      count = (Integer) queueProperties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT);

      //清空 未读消息
      if(count > 0){
        rabbitAdmin.purgeQueue(queueName,true);
      }

    }

    //完成新消息提醒即可，只需要获取消息的数量 （点赞通知作者---------------------------------）
    //获取新消息的数量
    String userQueueName = "article_thumbup_" + userId;
    Properties userQueueProperties = rabbitAdmin.getQueueProperties(userQueueName);

    Integer thumbupCount = 0;
    if(userQueueProperties != null){
      thumbupCount = (Integer) userQueueProperties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT);

      //清空 未读消息
      if(thumbupCount > 0){
        rabbitAdmin.purgeQueue(userQueueName,true);
      }

    }

    //封装返回的数据
    HashMap<Object, Object> map = new HashMap<>();
    map.put("sysNoticeCount",count);
    map.put("userNoticeCount",thumbupCount);
    Result result = new Result(true, StatusCode.OK,"查询成功", map);

    //把数据发送给数据
    channel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(result)));


    //为用户的消息通知队列注册监听器 -------------------------------------------------------
    //便于用户在线时，一旦有新文章发布消息，主动推送给用户
    //从spring容器中获取消息监听容器
    SimpleMessageListenerContainer sysNoticeContainer =
            (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext().
                    getBean("sysNoticeContainer");

    //便于作者在线时，一旦有点赞消息，主动推送给作者
    //从spring容器中获取消息监听容器
    SimpleMessageListenerContainer userNoticeContainer =
            (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext().
                    getBean("userNoticeContainer");

    sysNoticeContainer.addQueueNames(queueName);
    userNoticeContainer.addQueueNames(userQueueName);
  }


}
