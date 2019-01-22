package com.wan37.gameclient.adapter;

import com.wan37.common.proto.CmdProto;
import com.wan37.gameclient.view.MainView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/21 14:38
 * @version 1.00
 * Description: 客户端Protobuf适配器
 */

@Slf4j
public class ClientProtoAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        CmdProto.Cmd message = (CmdProto.Cmd) msg;

        MainView.output.append(message.getContent() + "\n");
        // 使用JTextArea的setCaretPosition();手动设置光标的位置为最后一行。人气颇高。使用方法也很简单
        MainView.output.setCaretPosition(MainView.output.getDocument().getLength());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
