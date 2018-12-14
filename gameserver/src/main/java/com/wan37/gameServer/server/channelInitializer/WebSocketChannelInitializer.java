package com.wan37.gameServer.server.channelInitializer;


import com.wan37.gameServer.server.dispatcher.RequestDispatcher;
import com.wan37.gameServer.server.dispatcher.WebSocketRequestDispatcher;
import com.wan37.gameServer.server.handler.MessageDecoder;
import com.wan37.gameServer.server.handler.MessageEncoder;
import com.wan37.gameServer.server.handler.WebSocketDecoder;
import com.wan37.gameServer.server.handler.WebSocketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 17:33
 * @version 1.00
 * Description: webSocket的通道初始化配置类
 */

@Component
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel>{



    @Resource
    private WebSocketRequestDispatcher webSocketRequestDispatcher;

    @Override
    protected void initChannel(SocketChannel ch)  {

        //ch.pipeline().addLast(new MessageEncoder());
        //解码器 (继承Netty的LengthFieldBasedFrameDecoder，处理TCP粘包拆包问题)
        ch.pipeline().addLast(new MessageDecoder(Integer.MAX_VALUE , 1, 4));

        ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));// 将多个消息转化成一个
        ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());// 解决大码流的问题
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));


        //ch.pipeline().addLast(new WebSocketEncoder());

        //ch.pipeline().addLast(new WebSocketDecoder(Integer.MAX_VALUE , 1, 4));


        ch.pipeline().addLast("http-server",webSocketRequestDispatcher);



    }
}
