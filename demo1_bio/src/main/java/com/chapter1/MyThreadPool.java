package com.chapter1;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author junius
 * @date 2022/09/21 11:33
 **/
public class MyThreadPool {

    public static ThreadPoolExecutor getThreadPool(){
        return new ThreadPoolExecutor(
                20,
                200,
                10,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
