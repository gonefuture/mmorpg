package com.wan37.common.entity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 15:28
 * @version 1.00
 * Description: 通用服务id，服务端根据这个字节分发请求
 * 1. 1000 - 1999 之间的服务与玩家，角色登陆相关
 * 2. 2000 - 2999 之间的服务与获取游戏内信息有关
 * 1. 1000 - 1999 之间的服务与角色移动相关
 *
 */
public enum MsgId {

    // 打招呼
    HEllO(1000),

    // 玩家登陆
    PLAYER_LOGIN(1001),

    // 角色登陆
    GAME_ROLE_LOGIN(1002),



    // 移动
    MOVE(3000);


    // MsgId
    private int msgId;

    MsgId(int msgId) {
        this.msgId = msgId;

    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "MsgId{" +
                "msgId=" + msgId +
                '}';
    }
}
