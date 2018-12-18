package com.wan37.gameServer.game.trade.service;

import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 12:24
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class TradeService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService sceneService;

    @Resource
    private BagsService bagsService;


    /**
     *  向玩家发起交易邀请
     * @param ctx 通道上下文
     * @param targetId 目标玩家id
     */
    public void initiateTrade(ChannelHandlerContext ctx, Long targetId) {

    }

    public void beginTrade(ChannelHandlerContext ctx) {
    }

    public void confirmTrade(ChannelHandlerContext ctx) {
    }

    public void tradeMoney(ChannelHandlerContext ctx, Long moneyNumber) {
    }

    public void tradeGoods(ChannelHandlerContext ctx, Long itemId) {
    }
}
