package com.wan37.gameclient;


import com.wan37.common.entity.Cmd;
import com.wan37.common.proto.CmdProto;
import com.wan37.gameclient.adapter.ClientProtoAdapter;


import com.wan37.gameclient.view.MainView;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.Channel;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/11 16:38
 * @version 1.00
 * Description: JavaLearn
 */

public class GameClient {


    public GameClient() {
    }

    public GameClient(String ip) {
        IP = ip;
    }

    public static Channel channel = null;

    /**   39.108.88.129 **/
    private static String IP = "gonefuture.top";

    private static int PORT = 8000;

    static  {
        MainView mainView = new MainView();
    }


    public void run() {
        //设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动附注类
        Bootstrap bootstrap = new Bootstrap();
        //指定所使用的NIO传输channel
        bootstrap.group(workerGroup).channel(NioSocketChannel.class);

        ClientProtoAdapter clientProtoAdapter = new ClientProtoAdapter();
        clientProtoAdapter.setServerIp(IP);

        //指定客户端初始化处理
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("ping", new IdleStateHandler(60, 60, 60, TimeUnit.SECONDS))
                        .addLast(new ProtobufVarint32FrameDecoder())
                        .addLast("proto-decoder",
                                new ProtobufDecoder(CmdProto.Cmd.getDefaultInstance()))
                        .addLast(new ProtobufVarint32LengthFieldPrepender())
                        .addLast("proto-encoder", new ProtobufEncoder())
                        // 处理器
                        .addLast(clientProtoAdapter)
                ;
                channel = ch;
            }
        });
        try {
            //连接服务
            Channel channel = bootstrap.connect(IP, PORT).sync().channel();

            // 循环监听输入
            loop();
        }catch (Exception e) {
            System.out.println("=========== 发生错误 =========");
            e.printStackTrace();

            System.out.println("=========== 尝试重连 =========");
            run();
        }
    }


    /**
     *  循环接受输入
     * @throws IOException 连接异常
     */
    private void loop() throws IOException {
        MainView.output.append("========== 连接服务器  "+ IP +"  成功,当前连接是 "+channel.id()+"==========\n");
        while (true) {
            System.out.println("请输入您的操作：  操作 + 数据（多个数据之间用空格隔开） (或者请使用指令` show_cmd `查看指令说明)");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String content = reader.readLine();
            System.out.println("客户端输入： "+content);
            if (StringUtils.isNotEmpty(content)) {
                if (StringUtils.equalsIgnoreCase(content, "q")) {
                    System.exit(1);
                }
                String[] array = content.split("\\s+");
                Cmd msgId = Cmd.findByCommand(array[0], Cmd.UNKNOWN);
                CmdProto.Cmd cmd = CmdProto.Cmd.newBuilder()
                        .setMgsId(msgId.getMsgId())
                        .setContent(content).build();
                //向服务端发送内容
                channel.writeAndFlush(cmd);
            }
        }
    }



    public static void main(String[] args) {
        if (args.length > 0) {
            IP = args[0];
        }
        new GameClient().run();
    }
}