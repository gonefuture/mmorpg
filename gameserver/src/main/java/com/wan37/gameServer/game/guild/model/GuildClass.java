package com.wan37.gameServer.game.guild.model;

import java.util.Set;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 16:20
 * @version 1.00
 * Description: mmorpg
 */
public enum  GuildClass {


    President("会长"),

    Vice_President("副会长"),

    Elite("精英"),

    Ordinary_Member("普通会员")

    ;


    GuildClass(String className, Set<String> power) {
        this.className = className;
        this.power = power;
    }


    GuildClass(String className) {
        this.className = className;

    }

    private String className;
    private Set<String> power;
}
