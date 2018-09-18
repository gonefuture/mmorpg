package com.wan37.gameServer.entity.role;

import com.wan37.gameServer.entity.map.Position;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 15:41
 * @version 1.00
 * Description: 角色骨架类
 */
@Data
public abstract class Role {
    // 角色名字
    private String name;
    private long hp;
    private long mp;

    // 所处位置
    private Position position;

    // 角色状态

    //public String roleState();


    Role(String name, long hp, long mp) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
    }

}
