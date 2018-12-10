package com.wan37.gameServer.game.gameRole.service;


import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.roleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.model.Player;

import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;


import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
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
    private BufferService bufferService;

    @Resource
    private RolePropertyService rolePropertyService;

    @Resource
    private BagsService bagsService;

    @Resource
    private EquipmentBarService equipmentBarService;


    public Player getPlayerByCtx(ChannelHandlerContext ctx) {
        return playerCacheMgr.get(ctx.channel().id().asLongText());
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







    public Player getPlayer(String channelId) {
        return playerCacheMgr.get(channelId);
    }




    public void computeAttack(Player player) {

        Map<Integer, RoleProperty> rolePropertyMap = player.getRolePropertyMap();
        // 基础攻击力
        int basicAttack = Optional.ofNullable(rolePropertyMap.get(4).getValue()).orElse(30);

        // 力量
        int power  = Optional.ofNullable(rolePropertyMap.get(5).getValue()).orElse(100);


        player.setAttack(basicAttack + power);
    }






    /**
     *  初始化角色, 注意，加载循序不能错。
     * @param player 角色
     */
    public void initPlayer(Player player) {

        // 加载属性
        rolePropertyService.loadRoleProperty(player);

        // 计算初始战力
        computeAttack(player);


        // 加载背包
        bagsService.loadBag(player);

        // 加载武器栏
        equipmentBarService.load(player);


        // 施放一个回蓝的buffer
        Buffer buffer = bufferService.getTBuffer(105);
        bufferService.startBuffer(player,buffer);

    }









}
