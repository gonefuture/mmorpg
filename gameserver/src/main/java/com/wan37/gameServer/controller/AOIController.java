package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.Player;

import com.wan37.gameServer.service.AOIService;
import com.wan37.gameServer.service.PlayerDataService;
import com.wan37.mysql.pojo.entity.TGameObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:35
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class AOIController implements IController {

    @Resource
    private AOIService aoiService;

    @Resource
    private PlayerDataService playerDataService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        HashMap<String,Object>  result = new HashMap<>();
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());

        List<TGameObject> tGameObjectList = aoiService.aoi(player.getScene());
        List<Player> playerList = aoiService.getPlayerInScene(player.getScene());


        if (tGameObjectList.size() != 0) {
            result.put("这里有玩家:",playerList);
            result.put("发现： ", tGameObjectList);
        } else {
            result.put("我发现： ","这个地方空无一物");
        }
        log.debug("当前场景的玩家对象： "+tGameObjectList);
        message.setFlag((byte)1);
        message.setContent(JSON.toJSONString(result).getBytes());
        ctx.writeAndFlush(message);
    }


}
