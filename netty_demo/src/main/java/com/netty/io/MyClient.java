package com.netty.io;

import java.io.IOException;
import java.net.Socket;

/**
 * @ClassName MyClient
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月13日 0:04
 * @Version 1.0.0
*/
public class MyClient {

  public static void main(String[] args) {

    for (int i = 0; i < 5; i++){
      ClientDemo clientDemo = new ClientDemo();
      clientDemo.start();
    }

  }

  static class ClientDemo extends Thread{
    @Override
    public void run() {

      try {
        Socket socket = new Socket("127.0.0.1",8888);

        while (true){
          socket.getOutputStream().write("测试数据".getBytes());
          socket.getOutputStream().flush();

          Thread.sleep(2000);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }
}
