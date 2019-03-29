package com.wan37.common.entity;

import com.wan37.common.proto.CmdProto;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 10:14
 * @version 1.00
 * Description: 消息实体类，定义协议格式，防止拆包粘包
 *
    服务器和客户端通信，要基于一定的格式，这个格式成为通信协议。我们的通信协议是这样的：
    下面的内容按顺序排列：
    1.一个字节：0xFF ，是一个开始标志
    2.四个字节：消息长度，Netty需要这个字段处理TCP粘包拆包问题
    3.四个字节：msgId，服务端根据这个字节分发请求
    4.一个字节：type，表示消息的类型，1代表JSON格式
    5.一个字节：flag，表示消息处理是否成功（1表示成功，0表示失败）
    6.其余字节：消息内容

 */
@Data
public class Message {

    /** msgId，服务端根据这个字节分发请求 **/
    private int msgId;
    /** 消息内容 **/
    private String content;



    public CmdProto.Cmd toProto() {
        return CmdProto.Cmd.newBuilder()
                .setMgsId(msgId)
                .setContent(content)
                .build();
    }


}
