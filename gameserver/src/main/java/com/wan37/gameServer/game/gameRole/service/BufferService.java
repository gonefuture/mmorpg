package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.manager.BufferCacheMgr;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;


import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 10:44
 * @version 1.00
 * Description: buffer管理服务
 */

@Service
@Slf4j
public class BufferService {


    @Resource
    private TaskManager taskManager;

    @Resource
    private BufferCacheMgr bufferCacheMgr;

    @Resource
    private NotificationManager notificationManager;


    public boolean startBuffer(Creature creature, Buffer buffer) {
        // 记录开始时间
        buffer.setStartTime(System.currentTimeMillis());
        creature.getBufferList().add(buffer);

        if (buffer.getDuration() != -1) {


            Future cycleTask = taskManager.scheduleAtFixedRate(0,buffer.getIntervalTime(),
                    () -> {
                        creature.setHp(creature.getHp() + buffer.getHp());
                        creature.setMp(creature.getMp() + buffer.getMp());
                        // 如果是玩家，进行通知
                        if (creature instanceof Player) {
                            notificationManager.notifyPlayer((Player) creature,MessageFormat.format(
                                    "你身上的buffer {0}  对你造成影响, hp{1} ,mp{2}",
                                    buffer.getName(),buffer.getHp(),buffer.getMp()
                            ));
                        }

                        log.debug(" buffer周期任务 {}", new Date());
                    }
                    );

            taskManager.schedule(buffer.getDuration(),
                    () -> {
                        // 过期移除buffer
                        creature.getBufferList().remove(buffer);
                        cycleTask.cancel(false);

                        // 如果是玩家，进行通知
                        if (creature instanceof Player) {
                            notificationManager.notifyPlayer((Player) creature,MessageFormat.format(
                                    "你身上的buffer {0}  结束",buffer.getName()
                            ));
                        }
                        log.debug(" buffer过期清除定时器 {}", new Date());
                        return null;
                    });
        }

        return true;
    }





    public Buffer getTBuffer(int tBufferId) {
        return bufferCacheMgr.get(tBufferId);
    }


    public boolean removeBuffer(Player player, Buffer tBuffer) {
        if (player != null && tBuffer != null) {
            player.getBufferList().remove(tBuffer);
            return true;
        } else {
            return false;
        }
    }

}
