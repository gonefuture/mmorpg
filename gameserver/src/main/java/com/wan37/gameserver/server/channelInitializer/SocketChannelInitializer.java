package com.wan37.gameserver.server.channelInitializer;

import com.wan37.gameserver.server.dispatcher.RequestDispatcher;
import com.wan37.gameserver.server.handler.MessageDecoder;
import com.wan37.gameserver.server.handler.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 17:18
 * @version 1.00
 * Description: 普通的socket
 */
@Component
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Resource
    private RequestDispatcher requestDispatcher;


    @Override
    protected void initChannel(SocketChannel ch)  {

        ChannelPipeline pipeline = ch.pipeline();

         pipeline.addLast("encode",new MessageEncoder())
                //解码器 (继承Netty的LengthFieldBasedFrameDecoder，处理TCP粘包拆包问题)
                .addLast("encoder",new MessageDecoder(Integer.MAX_VALUE , 1, 4))
                // 消息业务分派器
                .addLast("dispatcher",requestDispatcher)
        ;

    }
}
