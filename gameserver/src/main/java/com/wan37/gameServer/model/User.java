package com.wan37.gameServer.model;

import com.wan37.mysql.pojo.entity.TUser;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 9:48
 * @version 1.00
 * Description: mmorpg
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends TUser {

    private ChannelHandlerContext channelHandlerContext;

}
