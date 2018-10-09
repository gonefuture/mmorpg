package com.wan37.common.entity;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/9 12:12
 * @version 1.00
 * Description: mmorpg
 */
@Data
public class EorroMsg {

    // 错误码
    int code;

    // 错误信息
    String msg;

    public EorroMsg() {

    }

    public EorroMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
