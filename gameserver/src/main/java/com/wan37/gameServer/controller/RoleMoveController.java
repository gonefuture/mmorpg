package com.wan37.gameServer.controller;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 15:51
 * @version 1.00
 * Description: JavaLearn
 */
public class RoleMoveController {

    private ByteBuf in;

    public RoleMoveController(ByteBuf byteBuf) {
        this.in = byteBuf;
    }


    public ByteBuf result() {
        return Unpooled.copiedBuffer("你移动的方向是"+in.toString(CharsetUtil.UTF_8),
                CharsetUtil.UTF_8);
    }
}
