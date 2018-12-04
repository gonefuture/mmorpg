package com.wan37.common.entity;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/9 12:12
 * @version 1.00
 * Description: 信息实体类
 */
@Data
public class Msg {

    // 错误码
    private int code;

    // 错误信息
    private String msg;

    // 返回的结果
    private Object result;

    public Msg() {

    }

    public Msg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Msg(int code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
}
