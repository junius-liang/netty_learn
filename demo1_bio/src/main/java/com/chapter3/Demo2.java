package com.chapter3;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author junius
 * @date 2022/10/01 15:36
 * 网络编程
 **/
public class Demo2 {

    @Test
    /**
     * IP：唯一标识互联网的一台主机
     * Java使用InetAddress标识
     * 分类: ipv4 ipv6
     */
    public void test1() throws UnknownHostException {
        //InetAddress 表示ip
        InetAddress inetAddress1 = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress1);
        InetAddress inetAddress2 = InetAddress.getByName("127.0.0.1");
        System.out.println(inetAddress2);

        //获取本地ip
        InetAddress inetAddress3 = InetAddress.getLocalHost();
        System.out.println(inetAddress3);

        //通过InetAddress 获取域名
        String hostName = inetAddress1.getHostName();
        System.out.println(hostName); //www.baidu.com
        //通过InetAddress 获取ip
        String hostAddress = inetAddress1.getHostAddress();
        System.out.println(hostAddress);//180.101.49.12
    }


    @Test
    /**
     * 端口号标识正在计算机运行的进程
     * 不同进程有不同的端口号
     * 端口分类:
     *          1、公认端口：0-1023，被预先定义的服务通信占有
     *          2、注册端口：1024-49151，分配给用户进程或应用程序
     *          3、动态私有端口：49152-65535
     * ip+端口=套接字（Socket）
     */
    public void test2() throws Exception {}

    /**
     * TCP 网络编程
     * @throws Exception
     */
    @Test
    public void test3_Client() throws Exception {
        //创建套接字
        InetAddress serverIp = InetAddress.getByName("localhost");
        Socket socket = new Socket(serverIp,8080);
        OutputStream os = socket.getOutputStream();
        os.write("客户端发送一条消息".getBytes());
        os.close();
    }

    @Test
    public void test3_Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

    @Test
    /**
     *  UDP网络编程
     */
    public void test4_Client() throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket();
        String msg = "Hello udp";
        InetAddress serverIp = InetAddress.getLocalHost();
        int port = 8080;
        DatagramPacket packet = new DatagramPacket(msg.getBytes(),0,msg.length(),serverIp,port);
        datagramSocket.send(packet);
        datagramSocket.close();
    }

    @Test
    public void test4_Server() throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(8080);
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data,0,data.length);
        datagramSocket.receive(packet);
        System.out.println(new String(packet.getData(),0,packet.getLength()));
        datagramSocket.close();
    }

    @Test
    /**
     * URL:统一资源定位符，对应互联网的某一资源地址
     * 组成：协议+主机名+端口号+资源地址+参数列表
     */
    public void test5() throws Exception {
        URL url = new URL("https://programmercarl.com/0454.%E5%9B%9B%E6%95%B0%E7%9B%B8%E5%8A%A0II.html");
        //获取主机名称
        String host = url.getHost();
        System.out.println(host);
        //获取协议名
        String protocol = url.getProtocol();
        System.out.println(protocol);
        //获取端口号
        int port = url.getPort();
        System.out.println(port);
        //获取文件路径
        String path = url.getPath();
        System.out.println(path);
        //获取文件名
        System.out.println(url.getFile());
        //获取参数列表
        System.out.println(url.getQuery());
    }
}
