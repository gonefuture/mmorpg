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
    protected long id;
    protected String name;
    protected long hp;
    protected long mp;

    // 所处位置
    protected Position position;

    // 角色状态

    //public String roleState();


    public Role(){

    }

    public Role(Long id, String name, long hp, long mp) {
        this.id =  id;
        this.name = name;
        this.hp = hp;
        this.mp = mp;
    }

}
