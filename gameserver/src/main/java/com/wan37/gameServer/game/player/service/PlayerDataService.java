package com.wan37.gameServer.game.player.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.friend.model.Friend;
import com.wan37.gameServer.game.mission.service.MissionService;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.roleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.player.model.Player;

import com.wan37.gameServer.game.player.manager.PlayerCacheMgr;


import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TimedTaskManager;
import com.wan37.gameServer.model.Creature;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:07
 * @version 1.00
 * Description: mmorpg
 */
@Service
@Slf4j
public class PlayerDataService {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private TPlayerMapper tPlayerMapper;

    @Resource
    private RolePropertyService rolePropertyService;

    @Resource
    private BagsService bagsService;

    @Resource
    private EquipmentBarService equipmentBarService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private MissionService missionService;


    /**
     *  通过上下文查找玩家
     * @param ctx   上下文
     * @return  玩家
     */
    public Player getPlayerByCtx(ChannelHandlerContext ctx) {
        return playerCacheMgr.getPlayerByCtx(ctx);
    }

    /**
     *  通过玩家id查找玩家，如果玩家不在线，从数据库中寻找
     * @param playerId  玩家id
     * @return 如果存在玩家记录则返回玩家，不存在则返回空，注意玩家可能尚未初始化
     */
    public Player getPlayerById(long playerId) {
        ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(playerId);
        if(Objects.nonNull(ctx)) {
            return getPlayerByCtx(ctx);
        } else {
               TPlayer tPlayer = findTPlayer(playerId);
               if (Objects.nonNull(tPlayer)) {
                   Player player = new Player();
                   BeanUtils.copyProperties(tPlayer,player);
                   return player;
               } else {
                   return null;
               }
        }
    }


    /**
     *  通过玩家id查找在线玩家
     * @param playerId  玩家id
     * @return 如果玩家在线则返回玩家，如果不在线则返回空
     */
    public Player getOnlinePlayerById(long playerId) {
        ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(playerId);
        if(null != ctx) {
            return getPlayerByCtx(ctx);
        } else {
            return null;
        }
    }





    /**
     *   通过数据库查找玩家
     * @param playerId 玩家id
     * @return 数据库的玩家实体
     */
    public TPlayer findTPlayer(long playerId) {
        return tPlayerMapper.selectByPrimaryKey(playerId);
    }





    public Player getPlayer(ChannelHandlerContext ctx) {
        return playerCacheMgr.getPlayerByCtx(ctx);
    }


    /**
     *  计算攻击力
     * @param player 玩家
     */
    public void computeAttack(Player player) {

        Map<Integer, RoleProperty> rolePropertyMap = player.getRolePropertyMap();
        // 基础攻击力
        int basicAttack = Optional.ofNullable(rolePropertyMap.get(4).getValue()).orElse(30);

        // 力量
        int power  = Optional.ofNullable(rolePropertyMap.get(5).getValue()).orElse(100);


        player.setAttack(basicAttack + power);
    }


    /**
     *     检测玩家是否死亡
     * @param casualty 伤害承受者
     * @param murderer 攻击发起者
     */
    public synchronized boolean isPlayerDead(Player casualty, Creature murderer) {

        if (casualty.getHp() < 0){
            casualty.setHp((long)0);
            casualty.setState(-1);

            // 广播并通知死亡的玩家
            notificationManager.playerDead(murderer,casualty);

            gameSceneService.moveToScene(casualty,12);
            notificationManager.notifyPlayer(casualty,casualty.getName()+"  你已经在墓地了,十秒后复活 \n");

            TimedTaskManager.schedule(
                    10000, () -> {
                        casualty.setState(1);
                        initPlayer(casualty);
                        notificationManager.notifyPlayer(casualty,casualty.getName()+"  你已经复活 \n");
                    }
            );
            return true;
        } else {
            return false;
        }

    }


    /**
     *  初始化等级
     * @param player 玩家
     */
    private void initLevel(Player player) {
        player.setLevel(player.getExp()/100);
    }

    public void initFriend(Player player) {

        // 如果玩家好友列表为空，初始化玩家列表
        if (!Strings.isNullOrEmpty(player.getFriends()) && player.getFriendMap().isEmpty()) {
            Map<Long, Friend> friendMap = JSON.parseObject(player.getFriends(),
                    new TypeReference<Map<Long,Friend>>(){});
            player.getFriendMap().putAll(friendMap);
        }
    }



    /**
     *  初始化角色, 注意，加载循序不能错。
     * @param player 角色
     */
    public void initPlayer(Player player) {
        // 初始化等级
        initLevel(player);

        // 初始化朋友
        initFriend(player);

        // 加载属性
        rolePropertyService.loadRoleProperty(player);

        // 计算初始战力
        computeAttack(player);

        // 加载背包
        bagsService.loadBag(player);

        // 加载武器栏
        equipmentBarService.load(player);

        // 加载任务进度
        missionService.playerMissionProgressInit(player.getId());

        // 加载到场景中
        gameSceneService.initScene(player);

        log.debug("player {}", player);

    }




}
