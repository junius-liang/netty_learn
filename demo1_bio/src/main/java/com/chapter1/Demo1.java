package com.chapter1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author junius
 * @date 2022/09/21 11:19
 **/
public class Demo1 {
    public static void main(String[] args) throws IOException {
        //创建一个ServerSocket,并指定监听端口
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动....");

        //通过死循环感知连接
        while (true){
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("一个客户端连接进来了....");

            //创建一个线程池来处理客户端的请求
            ThreadPoolExecutor threadPool = MyThreadPool.getThreadPool();
            threadPool.execute(()->{
                try {
                    connectHandler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void connectHandler(Socket socket) throws IOException {
        //创建一个byte数组，用于存储从inputStream中读取的数据
        byte[] bytes = new byte[1024];

        InputStream inputStream = socket.getInputStream();
        while (true){
            //从输入流读数据，写到byte数组中
            int read = inputStream.read(bytes);
            //判断流中是否有数据
            if (read!=-1){
                System.out.println(new String(bytes));
            }else {
                break;
            }
        }

    }
}
