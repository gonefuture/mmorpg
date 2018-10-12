package com.wan37.gameServer.entity;

import com.wan37.mysql.pojo.entity.TBuffer;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/12 17:02
 * @version 1.00
 * Description:  状态实体类
 */

@Data
public class Buffer extends TBuffer {

    private long startTime;


}
