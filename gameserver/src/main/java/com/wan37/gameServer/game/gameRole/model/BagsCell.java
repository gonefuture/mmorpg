package com.wan37.gameServer.game.gameRole.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/5 10:50.
 *  说明：
 */

import com.wan37.gameServer.game.things.model.Things;
import com.wan37.mysql.pojo.entity.TItem;
import lombok.Data;

/**
 * <pre> </pre>
 */
@Data
public class BagsCell {

    private TItem tItem;


    private Things things;
}
