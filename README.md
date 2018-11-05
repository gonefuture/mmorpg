# mmorpg
A game server for mmorpg game based on Netty, SpringBoot, MyBatis。

## 简介

这是一个多人在线文字冒险游戏，

## 安装运行


## 客户端指令

客户端通过MsgId和参数来发送服务指令，MsgId(整型)部分如下

    // 玩家登陆, 参数为 用户账号和密码，例：  2 123456
    USER_LOGIN("login",1001),

    // 加载游戏列表 无参数，例： 1002
    LIST_GAME_ROLES("list_roles",1002),

    // 角色登陆 ，参数为 当前用户下的角色id，例： load 1313
    PLAYER_LOGIN("load",2001),

    // 玩家退出，无参数， 例： exit
    PLAYER_EXIT("exit",2002),

    // 展示背包
    SHOW_BAGS("show_bags",2003),


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

    // 与NPC，怪物对话
    DIALOG("dialog",4001),

    // 不知道的命令
    UNKNOWN("unknown", 0000)
