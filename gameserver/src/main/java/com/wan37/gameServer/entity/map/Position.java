package com.wan37.gameServer.entity.map;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 15:31
 * @version 1.00
 * Description: 位置类
 */

@Data
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
