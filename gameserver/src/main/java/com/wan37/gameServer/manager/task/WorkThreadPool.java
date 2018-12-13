package com.wan37.gameServer.manager.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/13 15:53
 * @version 1.00
 * Description: 业务工作线程池
 */

@Slf4j
@Component
public class WorkThreadPool {

    public static ExecutorService WorkThreadPool = Executors.newFixedThreadPool(20);
}
