package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Buffer;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.BufferCacheMgr;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.mysql.pojo.entity.TBuffer;
import org.springframework.beans.BeanUtils;
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
        Buffer buffer = new Buffer();
        BeanUtils.copyProperties(tBuffer,buffer);
        if (player != null ) {
            // 记录开始时间
            buffer.setStartTime(System.currentTimeMillis());
            player.getBufferList().add(buffer);

            if (buffer.getDuration() != -1) {
                taskManager.schedule(buffer.getDuration(),
                        () -> {
                            // 过期移除buffer
                            player.getBufferList().remove(buffer);
                            return null;
                        });
            }
            return true;
        } else {
            return false;
        }
    }



    public TBuffer getTBuffer(int tBufferId) {
        return bufferCacheMgr.get(tBufferId);
    }


    public boolean removeBuffer(Player player, TBuffer tBuffer) {
        if (player != null && tBuffer != null) {
            player.getBufferList().remove(tBuffer);
            return true;
        } else {
            return false;
        }
    }

}
