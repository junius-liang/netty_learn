package com.chapter2;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author junius
 * @date 2022/09/22 19:27
 **/
public class Demo2 {
    public static String path = "D:\\project\\study\\netty\\demo1_bio\\src\\main\\resources\\demo2.txt";

    public static void main(String[] args) throws Exception {
        String str = "Hello world";
        //创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //往buffer写数据
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        //获取文件输出流
        FileOutputStream outputStream = new FileOutputStream(path);
        //获取channel
        FileChannel fileChannel = outputStream.getChannel();
        //读写切换
        byteBuffer.flip();
        //将buffer的数据写道channel中
        fileChannel.write(byteBuffer);
        //关闭资源
        fileChannel.close();
    }
}
