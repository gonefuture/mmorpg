package com.wan37.gameserver.manager.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/13 15:53
 * @version 1.00
 * Description: 业务工作线程池
 */

@Slf4j
@Component
public final class WorkThreadPool {

    /**
     *  持久化的线程池，由于持久化不需要保证循序，所以直接用多线程的线程池。
     *  线程数 为 服务器核心*2+1
     */
    private static ThreadFactory persistenceThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("persistence-pool-%d").build();
    public static ExecutorService threadPool = new ThreadPoolExecutor(4,8,
            1000, TimeUnit.SECONDS,new LinkedBlockingQueue<>(100), persistenceThreadFactory);



}
