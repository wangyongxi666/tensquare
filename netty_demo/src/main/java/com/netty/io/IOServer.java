package com.netty.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName IOServer
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月12日 23:53
 * @Version 1.0.0
*/
//传统IO编程 服务端
public class IOServer {

  public static void main(String[] args) {

    try {
      ServerSocket serverSocket = new ServerSocket(8888);

      while (true){
        Socket socket = serverSocket.accept();

        new Thread(){
          @Override
          public void run() {
            String name = Thread.currentThread().getName();

            byte[] data = new byte[1024];

            InputStream inputStream = null;

            try {
              inputStream = socket.getInputStream();

              while (true){
                int len = 0;
                while ((len = inputStream.read(data)) != -1){
                  System.out.println("线程：" + name + ":" + new String(data,0,len));
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }

          }
        }.start();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
