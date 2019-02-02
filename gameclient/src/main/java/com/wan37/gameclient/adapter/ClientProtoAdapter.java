package com.wan37.gameclient.adapter;


import com.wan37.common.proto.CmdProto;
import com.wan37.gameclient.GameClient;
import com.wan37.gameclient.view.MainView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;




/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/21 14:38
 * @version 1.00
 * Description: 客户端Protobuf适配器
 */

public class ClientProtoAdapter extends ChannelInboundHandlerAdapter {

    private CmdProto.Cmd heartbeat = CmdProto.Cmd.newBuilder()
            .setMgsId(0)
            .setContent("心跳").build();

    private String serverIp;


    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        CmdProto.Cmd message = (CmdProto.Cmd) msg;
        System.out.println("客户端接收： "+message.getContent() + "\n");

        MainView.output.append(message.getContent() + "\n");
        // 使用JTextArea的setCaretPosition();手动设置光标的位置为最后一行。人气颇高。使用方法也很简单
        MainView.output.setCaretPosition(MainView.output.getDocument().getLength());
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("连接失效： "+ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    ctx.writeAndFlush(heartbeat);
                    break;
                case WRITER_IDLE:
                    ctx.writeAndFlush(heartbeat);
                    break;
                case ALL_IDLE:
                    ctx.writeAndFlush(heartbeat);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("连接出现错误：");
        cause.printStackTrace();
        System.out.println("=====重新连接服务器  ===");
        MainView.output.append("================重新连接服务器==========\n");
        new GameClient(serverIp).run();
    }

}
