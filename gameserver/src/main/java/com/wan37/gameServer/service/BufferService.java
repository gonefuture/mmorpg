package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.BufferCacheMgr;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.mysql.pojo.entity.TBuffer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 10:44
 * @version 1.00
 * Description: buffer管理服务
 */

@Service
public class BufferService {


    @Resource
    private TaskManager taskManager;

    @Resource
    private BufferCacheMgr bufferCacheMgr;


    public boolean startBuffer(Player player, TBuffer tBuffer) {

        if (player != null && tBuffer != null) {
            player.getBufferMap().put(tBuffer.getId(),tBuffer);
            // 判断buffer是否是持续循环的
            if (tBuffer.getLoopTime() != 0) {
                player.setMp(player.getMp() + tBuffer.getMp());
                player.setHp(player.getHp() + tBuffer.getHp() );
            }
            taskManager.schedule(tBuffer.getDuration(),
                    () -> {
                        // 过期移除buffer
                        player.getBufferMap().remove(tBuffer.getId());
                        return null;
                    });
            return true;
        } else {
            return false;
        }
    }



    public TBuffer getBuffer(int bufferId) {
        return bufferCacheMgr.get(bufferId);
    }



    public boolean removeBuffer(Player player, TBuffer tBuffer) {
        if (player != null && tBuffer != null) {
            player.getBufferMap().remove(tBuffer.getId());
            return true;
        } else {
            return false;
        }
    }

}
