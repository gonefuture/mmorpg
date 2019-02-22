package com.wan37.gameserver.server.channelInitializer;

import com.wan37.common.proto.CmdProto;
import com.wan37.gameserver.server.adapter.ServerProtoAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 17:18
 * @version 1.00
 * Description: 普通的socket
 */
@Component
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Resource
    private ServerProtoAdapter serverProtoAdapter;


    @Override
    protected void initChannel(SocketChannel ch)  {

        ChannelPipeline pipeline = ch.pipeline();

         pipeline
                 .addLast(new ProtobufVarint32FrameDecoder())
                 .addLast("proto-decoder", new ProtobufDecoder(CmdProto.Cmd.getDefaultInstance()))
                 .addLast(new ProtobufVarint32LengthFieldPrepender())
                 .addLast("proto-encoder", new ProtobufEncoder())
                 .addLast(serverProtoAdapter);
    }


}
