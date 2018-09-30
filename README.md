# mmorpg
A game server for mmorpg game based on Netty, SpringBoot, MyBatis。

## 简介

这是一个多人在线文字冒险游戏，

## 安装运行


## 客户端指令

客户端通过MsgId和参数来发送服务指令，MsgId(整型)部分如下

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
