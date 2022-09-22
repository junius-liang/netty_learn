package com.chapter2;

import java.nio.IntBuffer;

/**
 * @author junius
 * @date 2022/09/22 18:32
 **/
public class Demo1 {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i+321);
        }
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            int i = intBuffer.get();
            System.out.println(i);
        }
    }
}
