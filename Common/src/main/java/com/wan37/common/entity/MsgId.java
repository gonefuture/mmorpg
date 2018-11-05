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


    // 玩家登陆, 参数为 用户账号和密码，例： 1001 2 123456
    USER_LOGIN(1001),

    // 加载游戏列表 无参数，例： 1002
    LIST_GAME_ROLES(1002),

    // 角色登陆 ，参数为 当前用户下的角色id，例： 2001 1313
    PLAYER_LOGIN(2001),

    // 玩家退出，无参数， 例： 2002
    PLAYER_EXIT(2002),

    // 展示背包
    SHOW_BAGS(2003),


    // 角色移动， 参数 场景id ， 例： 3001 2
    MOVE(3001),

    // AOI, 显示场景内各种游戏对象
    AOI(3002),

    // 使用技能
    USE_SKILLS(3003),

    // buffer开始
    START_BUFFER(3004),

    // 装备武器装备
    EQUIP(3005),

    // 使用道具物品
    USE_ITEM(3006),

    // 与NPC，怪物对话
    DIALOG(4001),

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
