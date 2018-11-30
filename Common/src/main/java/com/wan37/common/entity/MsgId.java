package com.wan37.common.entity;

import lombok.Data;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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


    // 玩家登陆, 参数为 用户账号和密码，例： login 2 123456
    USER_LOGIN("login",1001),

    // 加载游戏列表 无参数，例： list_roles
    LIST_GAME_ROLES("list_roles",1002),

    // 角色登陆 ，参数为 当前用户下的角色id，例： 2001 1313
    PLAYER_LOGIN("load",2001),

    // 玩家退出，无参数， 例： exit
    PLAYER_EXIT("exit",2002),

    // 展示背包
    SHOW_BAGS("show_bags",2003),

    // 显示当前位置和相邻场景
    LOCATION("location",2004),

    // 展示装备栏
    SHOW_EQUIPMENTBAR("show_equip",2005),

    // 展示装备栏
    SHOW_Player("show_player",2006),


    // 角色移动， 参数 场景id ， 例： move 2
    MOVE("move",3001),

    // AOI, 显示场景内各种游戏对象
    AOI("aoi",3002),


    // 使用技能
    USE_SKILLS("use_skill",3003),

    // buffer开始
    START_BUFFER("start_buffer",3004),

    // 装备武器装备
    EQUIP("equip",3005),

    // 使用道具物品
    USE_ITEM("use_item",3006),

    // 角色使用普通攻击怪物
    COMMON_ATTACK("attack",3007),

    // 进入副本
    ENTER_INSTANCE("enter_instance",3008),


    // 与NPC，怪物对话
    DIALOG("dialog",4001),

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

    static {
        for (MsgId e : EnumSet.allOf(MsgId.class)) {
            commandMap.put(e.command,e);
            idMap.put(e.msgId,e);
        }
    }


    public static MsgId findByCommand(String command, MsgId defaultValue){
        MsgId value = commandMap.get(command);
        if(value == null){
            return defaultValue;
        }
        return value;
    }

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
