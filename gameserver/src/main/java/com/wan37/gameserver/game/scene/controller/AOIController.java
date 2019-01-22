package com.wan37.gameserver.game.scene.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;

import com.wan37.gameserver.game.player.model.Player;

import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.game.skills.model.Pet;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:35
 * @version 1.00
 * Description:
 */

@Component
@Slf4j
public class AOIController  {


    {
        ControllerManager.add(Cmd.AOI,this::aoi);
        ControllerManager.add(Cmd.LOCATION,this::location);
    }



    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService gameSceneService;


    /**
     *  * Description: AOI(Area Of Interest)，中文就是感兴趣区域。通
     *  *  * 俗一点说，感兴趣区域就是玩家在场景实时看到的区域；也就是AOI会随着英雄的移动改变而改变。
     * @param ctx   上下文
     * @param message 信息
     */
    private void aoi(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayer(ctx);

        GameScene gameScene = gameSceneService.getSceneByPlayer(player);

        List<GameScene> gameSceneList = gameSceneService.getNeighborsSceneByIds(gameScene.getNeighbors());

       StringBuilder  sb = new StringBuilder();
       sb.append("玩家 ").append(player.getName()).append("     ");
        sb.append(MessageFormat.format("    当前的位置是：{0}  {1}",gameScene.getId(),gameScene.getName())).append("\n");
        sb.append("   相邻的场景是：");
        gameSceneList.forEach(
                neighbor -> sb.append(MessageFormat.format("  {0}: {1} ",neighbor.getId(), neighbor.getName() ))
        );

        if (gameScene.getNpcs().isEmpty() && gameScene.getMonsters().isEmpty() && gameScene.getPlayers().size() == 0) {
             sb.append("我发现 ,这个地方空无一物");
        } else {

            sb.append("\n 场景内玩家: \n");
            gameScene.getPlayers().values().forEach( (p -> sb.append(p.displayData()).append("\n")));

            sb. append("场景内NPC： ").append("\n");
            gameScene.getNpcs().values().forEach( npc -> sb.append(npc.displayData()).append("\n"));

            sb. append("场景内怪物: ").append("\n");
            gameScene.getMonsters().values().forEach(
                    monster -> {
                        if (monster instanceof Pet) {
                            Pet pet = (Pet) monster;
                            sb.append(MessageFormat.format("id:{1} name:{2} hp:{3} mp:{4} ({0}的宠物) 目标:{5} \n",
                                    pet.getMaster().getName(),pet.getPetId(),pet.getName(),pet.getHp(),pet.getHp(),
                                    Objects.isNull(pet.getTarget())?"无":pet.getTarget().getName())) ;
                        } else {
                            sb.append(monster.displayData()).append("\n");
                        }
                    });
        }

        NotificationManager.notifyByCtx(ctx,sb);
    }


    private void location(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx);

        GameScene gameScene = gameSceneService.getSceneByPlayer(player);

        List<GameScene> gameSceneList = gameSceneService.getNeighborsSceneByIds(gameScene.getNeighbors());

        String location = MessageFormat.format("当前场景是： {0} \n",gameScene.getName() );
        StringBuilder neighbors = new StringBuilder();
        neighbors.append(location);
        neighbors.append("相邻的场景是： ");
        gameSceneList.forEach(
                neighbor -> neighbors.append(MessageFormat.format("{0}: {1} ",neighbor.getId(), neighbor.getName() ))
        );

        NotificationManager.notifyByCtx(ctx,neighbors);
    }


}
