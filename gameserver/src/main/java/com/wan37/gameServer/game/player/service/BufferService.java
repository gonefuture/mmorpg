package com.wan37.gameServer.game.player.service;

import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.player.model.Buffer;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.manager.BufferCacheMgr;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TimedTaskManager;


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
    private BufferCacheMgr bufferCacheMgr;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private CombatService combatService;


    public boolean startBuffer(Creature creature, Buffer buffer) {
        Buffer playerBuffer = new Buffer();
        playerBuffer.setName(buffer.getName());
        playerBuffer.setDuration(buffer.getDuration());
        playerBuffer.setHp(buffer.getHp());
        playerBuffer.setMp(buffer.getMp());
        playerBuffer.setIntervalTime(buffer.getIntervalTime());

        // 记录开始时间
        playerBuffer.setStartTime(System.currentTimeMillis());
        creature.getBufferList().add(playerBuffer);

        // 如果是buffer有不良效果
        if (buffer.getEffect() != 0) {
            creature.setState(buffer.getEffect());
        }


        // 如果buffer有持续时间
        if (buffer.getDuration() != -1) {
            // 如果间隔时间不为-1，即buffer间隔触发
            if (buffer.getIntervalTime() != -1) {
                Future cycleTask = TimedTaskManager.scheduleAtFixedRate(0,buffer.getIntervalTime(),
                        () -> {
                            creature.setHp(creature.getHp() + buffer.getHp());
                            creature.setMp(creature.getMp() + buffer.getMp());

                            // 如果是玩家，进行通知
                            if (creature instanceof Player) {
                                notificationManager.notifyPlayer((Player) creature,MessageFormat.format(
                                        "你身上的buffer {0}  对你造成影响, hp:{1} ,mp:{2} \n",
                                        buffer.getName(),buffer.getHp(),buffer.getMp()
                                ));
                                // 检测玩家是否死亡
                                playerDataService.isPlayerDead((Player) creature,null);
                            }

                        }
                );
                TimedTaskManager.scheduleWithData(buffer.getDuration(),() -> {
                    cycleTask.cancel(true);
                    return null;
                });
            }

            // buffer cd 处理
            TimedTaskManager.scheduleWithData(buffer.getDuration(),
                    () -> {
                        // 过期移除buffer
                        creature.getBufferList().remove(buffer);

                        // 恢复正常状态
                        creature.setState(1);


                        // 如果是玩家，进行通知
                        if (creature instanceof Player) {
                            notificationManager.notifyPlayer((Player) creature,MessageFormat.format(
                                    "你身上的buffer {0}  结束\n",buffer.getName()
                            ));
                            // 检测玩家是否死亡
                            playerDataService.isPlayerDead((Player) creature,null);
                        }
                        log.debug(" buffer过期清除定时器 {}", new Date());
                        return null;
                    });
        } else {
            // 永久buffer
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
