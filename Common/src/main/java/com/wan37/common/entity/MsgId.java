package com.wan37.common.entity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 15:28
 * @version 1.00
 * Description: mmorpg
 */
public enum MsgId {



    // 打招呼
    HEllO(1000),

    // 移动
    MOVE(2000);


    // MsgId
    private int msgId;

    MsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "MsgId{" +
                "msgId=" + msgId +
                '}';
    }
}
