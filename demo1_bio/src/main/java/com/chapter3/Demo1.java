package com.chapter3;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

/**
 * @author junius
 * @date 2022/09/27 10:41
 * Java IO流补充
 **/
public class Demo1 {
    @Test
    //使用流读文件
    public void demo1() throws Exception {
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo1.txt");
        FileReader reader = new FileReader(file);
        int data = reader.read();
        while (data !=-1){
            System.out.print((char) data);
            data  = reader.read();
        }
        reader.close();
    }

    @Test
    //使用流写文件
    public void demo2() throws Exception{
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo2.txt");
        //在原有文件追加内容
        FileWriter fileWriter = new FileWriter(file,true);
        //写出的操作
        fileWriter.write("I have a dream. \n");
        fileWriter.write("you need to have a dream. \n");
        fileWriter.close();
    }

    @Test
    //实现文件的复制
    public void demo3() throws Exception{
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo1.txt");
        File file1 = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo2.txt");
        FileReader reader = new FileReader(file);
        FileWriter writer = new FileWriter(file1);
        char[] chars = new char[10];
        int len;
        while ((len= reader.read(chars))!=-1){
            writer.write(chars,0,len);
        }
        System.out.println("文件复制成功");
        //必须关闭资源，不关闭复制不成功
        reader.close();
        writer.close();
    }

    @Test
    //实现文件的复制
    public void demo4() throws Exception {
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\resources\\images\\img.png");
        File file1 = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\resources\\images\\img_cp.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        byte[] bytes = new byte[10];
        int len;
        while ((len=fileInputStream.read(bytes))!=-1){
            fileOutputStream.write(bytes,0,len);
        }
        fileInputStream.close();
    }

    @Test
    /**
     * 缓冲流:构造函数包一个节点流
     * BufferInputStream
     * BufferOutputSrteam
     * BufferReader
     * BufferWriter
     */
    public void demo5() throws Exception{
        //缓冲流是对已有的流的包装
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo1.txt");
        FileReader fileInputStream = new FileReader(file);
       char[] chars = new char[20];
        BufferedReader stream = new BufferedReader(fileInputStream);
        int len;
        while ((len=stream.read(chars))!=-1){
            //此处不用换行输出，换行符自动识别输出
            System.out.print(new String(chars,0,len));
        }

    }

    /**
     *  转换流：字符流跟字节流的转换
     *  InputStreamReader：将字节的输入流转换成字符的输入流
     *  OutputStreamWriter：将字节的输出流转换成字符的输出流
     *
     *  编码与解码
     *  编码：char=>byte
     *  解码：byte=>char
     *
     */
    @Test
    public void demo6() throws Exception{
        File file = new File("D:\\project\\study\\netty\\demo1_bio\\src\\main\\java\\com\\chapter3\\demo1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader =  new InputStreamReader(fileInputStream);
        char[] chars = new char[5];
        int len;
        while ((len=inputStreamReader.read(chars))!=-1){
            System.out.print(new String(chars,0,len));
        }
        inputStreamReader.close();
        fileInputStream.close();
    }



    @Test
    public void demo7() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("","rw");
    }



}
