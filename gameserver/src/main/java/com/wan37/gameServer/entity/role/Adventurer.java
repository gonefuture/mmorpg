package com.wan37.gameServer.entity.role;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 15:18
 * @version 1.00
 * Description: 冒险者
 */

@Data
public class Adventurer extends Role {


    public Adventurer(String name, int hp, int mp) {
        super(name, hp, mp);
    }




}
