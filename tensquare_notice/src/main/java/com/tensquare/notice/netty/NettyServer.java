package com.tensquare.notice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName NettyServer
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 16:25
 * @Version 1.0.0
*/
public class NettyServer {

  public void start(int port){
    System.out.println("准备启动netty。。。。。。。。。。");
    System.out.println("服务器的端口号为：" + port);

    // 用于接受客户端的连接以及为已接受的连接创建子通道，一般用于服务端。
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    // 接受新连接线程
    NioEventLoopGroup boos = new NioEventLoopGroup();
    // 读取数据的线程
    NioEventLoopGroup work = new NioEventLoopGroup();

    //服务端执行
    serverBootstrap.group(boos,work)
//            .localAddress(port)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer() {

              @Override
              protected void initChannel(Channel channel) throws Exception {

                //请求消息解码器
                channel.pipeline().addLast(new HttpServerCodec());
                // 将多个消息转换为单一的request或者response对象
                channel.pipeline().addLast(new HttpObjectAggregator(65536));
                //处理WebSocket的消息事件
                channel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

                //创建自己的处理器
                MyWebSocketHandler myWebSocketHandler = new MyWebSocketHandler();
                channel.pipeline().addLast(myWebSocketHandler);
              }
            }).bind(port);
  }

}
