package com.wan37.gameserver.server.adapter;

import com.wan37.common.entity.Message;
import com.wan37.common.proto.CmdProto;
import com.wan37.gameserver.common.ErrorController;
import com.wan37.gameserver.common.IController;
import com.wan37.gameserver.game.player.service.PlayerQuitService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/21 12:26
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@ChannelHandler.Sharable
@Component
public class ServerProtoAdapter extends ChannelInboundHandlerAdapter {


    @Resource
    private ControllerManager controllerManager;

    @Resource
    private ErrorController errorController;

    @Resource
    private PlayerQuitService playerQuitService;



    /**
     *  当客户端连上服务器的时候触发此函数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端: " + ctx.channel().id() + " 加入连接");
        NotificationManager.notifyByCtx(ctx,"欢迎来到斯拉泽艾大陆");
        NotificationManager.notifyByCtx(ctx,"请使用魔法咒语 ` show_cmd ` 展现所有指令");

        NotificationManager.notifyByCtx(ctx,"测试账号登陆示例: login 1  123 ; login 2  123 ; login 3 123;");
    }

    /**
     * 当客户端断开连接的时候触发函数
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush("正在断开连接");

        // 将角色信息保存到数据库
        playerQuitService.savePlayer(ctx);

        // 清除缓存
        playerQuitService.cleanPlayerCache(ctx);
        log.info("客户端: " + ctx.channel().id() + " 已经离线");

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) {
        CmdProto.Cmd cmd = (CmdProto.Cmd) message;
        // 如果发送的是心跳，直接无视
        if (cmd.getMgsId() == 0) {
            return;
        }

        log.debug("==========》 服务端收到  {}",message);
        Message msg = new Message();
        msg.setMsgId(cmd.getMgsId());
        msg.setContent(cmd.getContent());

        IController controller = controllerManager.get(cmd.getMgsId());
        if (controller == null) {
            errorController.handle( ctx ,msg);
        } else {
            controllerManager.execute(controller,ctx,msg);
        }
    }



    /**
     *  玩家意外退出时保存是数据
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        log.error("服务器内部发生错误");
        NotificationManager.notifyByCtx(ctx,"出现了点小意外"+cause.getMessage());

        // 将角色信息保存到数据库
        playerQuitService.savePlayer(ctx);

        log.error("发生错误 {}", cause.getMessage());

        // 打印错误
        cause.printStackTrace();
    }

}
