package com.wan37.gameserver.game.mail.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/14 11:11
 * @version 1.00
 * Description: 默认发送者
 */
public enum  DefaultSender {

    /** 游戏管理者 */
    GM(0L),

    /** 拍卖行 */
    AuctionHouse(100L)

    ;

    private Long id;

    DefaultSender(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
