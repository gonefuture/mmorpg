package com.wan37.gameServer.util;


import com.wan37.common.entity.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 15:40
 * @version 1.00
 * Description: 参数校验工具
 */
public final class ParameterCheckUtil {



    public static String[] checkParameter(ChannelHandlerContext ctx, Message message,int parameterNumber) {
        String[] args;
        try {
            args = new String(message.getContent()).split("\\s+");
        }catch (IndexOutOfBoundsException e) {
            message.setFlag((byte) 1);
            message.setContent("您输入的参数数目不正确！".getBytes());
            throw new RuntimeException(e);
        }
        return args;
    }
}
