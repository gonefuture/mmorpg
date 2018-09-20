package com.wan37.gameClient;

import com.wan37.gameClient.coder.MessageDecoder;
import com.wan37.gameClient.coder.MessageEncoder;
import com.wan37.gameClient.handler.GameClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/11 16:38
 * @version 1.00
 * Description: JavaLearn
 */


@Component
@Slf4j
public class GameClient {

    private String ip;

    private int port;


    public GameClient() {
        this.ip = "127.0.0.1";
        this.port = 8000;
    }

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
                        // 编码器
                        .addLast(new MessageEncoder())
                        //解码器 (继承Netty的LengthFieldBasedFrameDecoder，处理TCP粘包拆包问题)
                        //.addLast(new MessageDecoder(Integer.MAX_VALUE, 1, 4))
                        ;//.addLast(new GameClientHandler());
            }
        });
        try {
            //连接服务
            Channel channel = bootstrap.connect(ip, port).sync().channel();
            while (true) {
                //向服务端发送内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String content = reader.readLine();
                if (StringUtils.isNotEmpty(content)) {
                    if (StringUtils.equalsIgnoreCase(content, "q")) {
                        System.exit(1);
                    }
                    log.debug("客户端发送的信息： "+content);
                    channel.writeAndFlush(Unpooled.copiedBuffer(content,CharsetUtil.UTF_8));

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

    @PostConstruct
    public void start() {
        try {
            log.info("启动客户端");
          run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}