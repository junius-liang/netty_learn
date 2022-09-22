package com.chapter2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author junius
 * @date 2022/09/22 19:49
 **/
public class Demo3 {
    public static void main(String[] args) throws IOException {
        String path = Demo2.path;
        FileInputStream fileInputStream = new FileInputStream(path);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(11);
        channel.read(buffer);
        buffer.flip();

        System.out.println(new String(buffer.array()));
    }
}
