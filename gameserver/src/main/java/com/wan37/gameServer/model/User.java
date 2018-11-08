package com.wan37.gameServer.model;

import com.wan37.mysql.pojo.entity.TUser;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 9:48
 * @version 1.00
 * Description: mmorpg
 */
@Data
public class User extends TUser {

    private String channelId;
}
