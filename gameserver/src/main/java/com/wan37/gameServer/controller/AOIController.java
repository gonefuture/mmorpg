package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.Monster;
import com.wan37.gameServer.entity.NPC;
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
import java.util.Map;

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

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());

        int sceneId = player.getScene();
        // 分别获取场景内的玩家和游戏对象
        Map<Long,NPC> npCMap = aoiService.getNPCs(sceneId );
        Map<Long,Monster> monsterMap = aoiService.getMonsters(sceneId);
        List<Player> playerList = aoiService.getPlayerInScene(sceneId );

        HashMap<String,Object>  result = new HashMap<>();
        if (npCMap.isEmpty() && monsterMap.isEmpty() && playerList.size() == 0) {
            result.put("我发现： ","这个地方空无一物");
        } else {
            result.put("场景内玩家:",playerList);
            result.put("场景内NPC： ", npCMap);
            result.put("场景内怪物",monsterMap);
        }
        log.debug("当前场景的玩家对象有{}个，分别为{} ",playerList.size(),playerList);
        message.setFlag((byte)1);
        message.setContent(JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect).getBytes());
        ctx.writeAndFlush(message);
    }


}
