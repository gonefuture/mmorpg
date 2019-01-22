package com.wan37.gameserver.server;


import com.wan37.gameserver.server.channelInitializer.SocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 11:36
 * @version 1.00
 * Description: 游戏服务端
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class GameServer {

    @Resource
    private SocketChannelInitializer socketChannelInitializer;



    //绑定端口
    private void bind(int port) throws Exception {
        // 逻辑线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 工作线程
        EventLoopGroup workGroup = new NioEventLoopGroup();

        // 启动器
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                //.option(ChannelOption.SO_BACKLOG, 1024) // 最大客户端连接数为1024
                //是否启用心跳保活机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childHandler(socketChannelInitializer);

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                System.out.println("Server starts success at port:"+port);
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8000;
        new GameServer().bind(port);
    }


    @PostConstruct
    public void start() {
        int port = 8000;
        try {
          bind(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
