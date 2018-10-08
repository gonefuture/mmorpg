# mmorpg
A game server for mmorpg game based on Netty, SpringBoot, MyBatis。

## 简介

这是一个多人在线文字冒险游戏，

## 安装运行


## 客户端指令

客户端通过MsgId和参数来发送服务指令，MsgId(整型)部分如下

    // 玩家登陆, 参数为 用户账号和密码，例： 1001 2 123456
    USER_LOGIN(1001),

    // 加载游戏列表 无参数，例： 1002
    LIST_GAME_ROLES(1002),

    // 角色登陆 ，参数为 当前用户下的角色id，例： 2001 1313
    PLAYER_LOGIN(2001),

    // 玩家退出，无参数， 例： 2002
    PLAYER_EXIT(2002),

    // 角色移动， 参数 场景id ， 例： 3001 2
    MOVE(3001),

    // AOI, 显示场景内各种游戏对象
    AOI(3002),

    // 使用技能
    USE_SKILLS(3003),

    // 与NPC，怪物对话
    DIALOG(4001),
