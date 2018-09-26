package com.wan37.common.entity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 14:15
 * @version 1.00
 * Description: 游戏地图标志枚举
 */
public enum MapMarker {

    // 普通道路，可以通行。
    ROAD("道路"),

    // 角色
    ROLE("角色"),

    // 山岭，不可通行
    HILL("山岭"),

    // 河流，不可通行
    RIVER("河流"),

    // 旅馆，
    HOTEL("旅馆");

    // 标记名称
    private final String name;

    MapMarker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 显示标志的名字
    @Override
    public String toString() {
        return this.name;
    }
}
