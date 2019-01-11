package com.wan37.gameserver.game.things.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/11 11:34
 * @version 1.00
 * Description: 物品种类
 */
public enum  ThingKind {


    /** 普通物品，占一个格子*/
    COMMON_THING(1),

    /** 装备 **/
    EQUIPMENT(2),

    /** 可堆叠 **/
    STACKABLE(3)

    ;

    private Integer kind;

    ThingKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getKind() {
        return kind;
    }}
