package com.wan37.common.entity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 15:28
 * @version 1.00
 * Description: 通用服务id，服务端根据这个字节分发请求
 * 1. 1000 - 1999 之间的服务与玩家，角色登陆相关
 * 2. 2000 - 2999 之间的服务与获取游戏内信息有关
 * 1. 1000 - 1999 之间的服务与角色场景相关
 *
 */
public enum MsgId {

    // 打招呼
    HEllO(1000),

    // 玩家登陆
    USER_LOGIN(1001),

    // 加载游戏列表
    LIST_GAME_ROLES(1002),

    // 角色登陆
    PLAYER_LOGIN(2001),

    // 角色移动
    MOVE(3001),

    // AOI, 显示场景内各种游戏对象
    AOI(3002),

    // 与NPC，怪物对话
    DIALOG(40001),

    ;
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
