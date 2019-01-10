package com.wan37.gameserver.game.trade.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/10 14:18
 * @version 1.00
 * Description: 拍卖模式
 */
public enum  AuctionMode {

    /**  竞价拍卖模式  */
    AT_AUCTION(1),

    /** 一口价模式 */
    SHELL_NOW(2)
    ;

    private Integer type;

    AuctionMode(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
