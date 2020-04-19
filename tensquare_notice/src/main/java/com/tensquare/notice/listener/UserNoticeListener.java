package com.tensquare.notice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.tensquare.notice.netty.MyWebSocketHandler;
import entity.Result;
import entity.StatusCode;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.util.HashMap;

/**
 * @ClassName SysNoticeListener
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 22:00
 * @Version 1.0.0
*/
public class UserNoticeListener implements ChannelAwareMessageListener{

  private static ObjectMapper mapper = new ObjectMapper();

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {

    //获取用户id  通过队列名称获取
    String queueName = message.getMessageProperties().getConsumerQueue();

    String userId = queueName.substring(queueName.lastIndexOf("_") + 1, queueName.length());


    //判断用户是否在线（是否已登录）
    io.netty.channel.Channel webSocketChannel = MyWebSocketHandler.userChannelMap.get(userId);

    if(webSocketChannel != null){
      //封装返回数据
      HashMap<String, Object> countMap = new HashMap<>();
      countMap.put("userNoticeCount",1);
      Result result = new Result(true, StatusCode.OK,"查询成功",countMap);

      //把数据通过webSocket主动推送用户
      webSocketChannel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(result)));
    }
  }
}
