package com.wan37.gameClient;


import com.wan37.common.entity.Message;
import com.wan37.gameClient.coder.MessageEncoder;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.Channel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/11 16:38
 * @version 1.00
 * Description: JavaLearn
 */

@Slf4j
@Component
public class GameClient {

    private String ip = "127.0.0.1";

    private int port = 8000;

    // 停止标志位
    private boolean stop = false;



    @PostConstruct
    public void run() throws IOException {
        //设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动附注类
        Bootstrap bootstrap = new Bootstrap();
        //指定所使用的NIO传输channel
        bootstrap.group(workerGroup).channel(NioSocketChannel.class);

        //指定客户端初始化处理
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new MessageEncoder())
                        //.addLast(new MessageDecoder(Integer.MAX_VALUE , 1, 4))

                        // 处理器1543

                        //.addLast(new GameClientHandler())
                ;
            }
        });
        try {
            //连接服务
            Channel channel = bootstrap.connect(ip, port).sync().channel();
            while (true) {
                System.out.println("请选择你的操作");
                //向服务端发送内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String content = reader.readLine();
                if (StringUtils.isNotEmpty(content)) {
                    if (StringUtils.equalsIgnoreCase(content, "q")) {
                        System.exit(1);
                    }
                    //log.debug("客户端发送的信息： "+content);
                    //channel.writeAndFlush(Unpooled.copiedBuffer(content,CharsetUtil.UTF_8));
                    Message message = new Message();
                    message.setMsgId(1001);
                    message.setType((byte)1);
                    message.setContent(content.getBytes());
                    channel.writeAndFlush(message);

                }
            }
            //指定所使用的NIO传输channel
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            workerGroup.shutdownGracefully();
            log.info("释放所有的资源");
        }
    }

    public static void main(String[] args) throws Exception {
        new GameClient().run();
    }
}