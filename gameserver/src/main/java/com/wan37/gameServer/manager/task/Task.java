package com.wan37.gameServer.manager.task;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 17:04
 * @version 1.00
 * Description: mmorpg
 */
public interface Task {

    void execute();

    void onFinish();

    void onTimeout();

    void tick();
}
