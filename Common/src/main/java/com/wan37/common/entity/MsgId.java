package com.wan37.common.entity;

import lombok.Data;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 15:28
 * @version 1.00
 * Description: 通用服务id，服务端根据这个服务id分发请求
 * 1. 1000 - 1999 之间的服务与玩家，角色登陆相关
 * 2. 2000 - 2999 之间的服务与获取游戏内信息有关
 * 1. 1000 - 1999 之间的服务与角色场景相关
 *
 */


public enum MsgId {


    // 玩家登陆, 参数为 用户账号和密码，例： login 2 123456
    USER_LOGIN("login",1001),

    // 加载游戏列表 无参数，例： list_roles
    LIST_GAME_ROLES("list_roles",1002),

    // 角色登陆 ，参数为 当前用户下的角色id，例： 2001 1313
    PLAYER_LOGIN("load",2001),

    // 玩家退出，无参数， 例： exit
    PLAYER_EXIT("exit",2002),

    // 展示背包
    SHOW_BAGS("bags",2003),

    // 整理背包
    PACK_BAG("pack_bag",12003),

    // 显示当前位置和相邻场景
    LOCATION("location",2004),

    // 展示装备栏
    SHOW_EQUIPMENT_BAR("equip_bar",2005),

    // 展示玩家属性
    SHOW_PLAYER("player",2006),


    // 角色移动， 参数 场景id ， 例： move 2
    MOVE("move",3001),

    // AOI, 显示场景内各种游戏对象
    AOI("aoi",3002),

    // 与npc谈话
    TALK_WITH_NPC("talk",13002),

    // 使用技能
    USE_SKILL("skill",13003),

    // 使用技能攻击怪物
    PVE_SKILL("pve_skill",3003),

    // buffer开始
    START_BUFFER("start_buffer",3004),

    // 装备武器装备
    EQUIP("equip",3005),

    // 使用道具物品
    USE_ITEM("use_item",3006),

    // 角色使用普通攻击怪物
    COMMON_ATTACK("attack",3007),

    // 进入副本
    ENTER_INSTANCE("instance",3008),

    // 退出副本
    EXIT_INSTANCE("exit_instance",3009),

    // 卸下武器，放回背包
    REMOVE_EQUIP("remove_equip",3010),

    // 普通攻击玩家
    PVP("pvp",3011),

    // 技能攻击玩家
    SKILL_PVP("pvp_skill",30012),


    // 私聊
    WHISPER("whisper",4001),

    // 公共聊天
    PUBLIC_CHAT("public_chat",4002),

    // 购买货物
    BUY_GOODS("buy",4003),

    // 展示商店货物
    SHOW_SHOP("shop",4004),

    // 发送邮件
    SEND_MAIL("send_mail",4005),

    // 邮件列表
    MAIL_LIST("mail_list",4006),

    // 接收邮件
    GET_MAIL("get_mail",4007),

    // 邀请组队
    INVITE_TEAM("invite",4008),

    // 加入队伍
    JOIN_TEAM("join",4009),

    // 离开队伍
    LEAVE_TEAM("leave",4010),

    // 查看队伍
    SHOW_TEAM("team",4011),

    // 团队副本
    TEAM_INSTANCE("team_instance",4012),


    // 发起交易
    INITIATE_TRADE("trade",4013),

    // 开始交易
    BEGIN_TRADE("begin_trade",4014),

    // 交易货物
    TRADE_GOODS("trade_goods",4015),

    // 交易金币
    TRADE_MONEY("trade_money",4016),

    // 确定交易
    CONFIRM_TRADE("trade_confirm",4017),

    // 创建公会
    GUILD_CREATE("guild_create",4018),

    // 查看公会
    GUILD_SHOW("guild_show",4019),

    // 公会捐献
    GUILD_DONATE("guild_donate",14019),

    // 加入公会
    GUILD_JOIN("guild_join",4020),

    // 授权公会成员
    GUILD_GRANT("guild_grant",4021),

    // 获取公会物品
    GUILD_TAKE("guild_take",4022),

    // 允许入会
    GUILD_PERMIT("guild_permit", 4023),

    // 退出公会
    GUILD_QUIT("guild_quit", 4024),

    // 显示已接任务
    MISSION_SHOW("mission", 4025),

    // 显示所有任务和成就
    ALL_MISSION("all_mission",4026),

    FRIEND_LIST("friend_list",4027),

    FRIEND_ADD("friend_add",4028),


   // 未知的命令
    UNKNOWN("unknown", 9999)

    ;
    // MsgId
    private String command;

    private Integer msgId;

    private static final Map<String, MsgId>  commandMap = new  HashMap<>();
    private static final Map<Integer, MsgId>  idMap = new  HashMap<>();

    MsgId(String command, Integer msgId) {
        this.command = command;
        this.msgId = msgId;
    }

    /*
     *  程序启动时将字符串的命令与枚举对象通过map关联起来
     */
    static {
        for (MsgId e : EnumSet.allOf(MsgId.class)) {
            commandMap.put(e.command,e);
            idMap.put(e.msgId,e);
        }
    }

    /**
     *  通过字符串命令查找命令枚举，如果找不到，返回一个默认的枚举对象
     * @param command 字符串命令
     * @param defaultValue 默认命令枚举
     * @return 一个相关服务的枚举
     */
    public static MsgId findByCommand(String command, MsgId defaultValue){
        MsgId value = commandMap.get(command);
        if(value == null){
            return defaultValue;
        }
        return value;
    }

    /**
     *  通过整型的服务id查找命令枚举，如果找不到，返回一个默认的枚举对象
     * @param msgId  服务id
     * @param defaultValue  默认命令枚举
     * @return 一个相关服务的枚举
     */
    public static MsgId find(int msgId,  MsgId defaultValue){
        MsgId value = idMap.get(msgId);
        if(value == null){
            return defaultValue;
        }
        return value;
    }



    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }


    @Override
    public String toString() {
        return "MsgId{" +
                "command='" + command + '\'' +
                ", msgId=" + msgId +
                '}';
    }
}
