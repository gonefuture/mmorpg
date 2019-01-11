package com.wan37.gameserver.game.trade.dao;

import com.wan37.mysql.pojo.entity.TAuctionItem;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/11 16:00
 * @version 1.00
 * Description: sql提供者
 */
public class AuctionSqlProvider {

    public String insertAndReturnKey(final TAuctionItem tAuctionItem) {
        return new SQL(){{
            INSERT_INTO("t_auction_item")
                    .VALUES("thing_info_id","#{thingInfoId}")
                    .VALUES("number","#{number}")
                    .VALUES("auction_price","#{auctionPrice}")
                    .VALUES("auction_mode","#{auctionMode}")
                    .VALUES("push_time","#{pushTime}")
                    .VALUES("bidders","#{bidders}");
        }}.toString();
    }
}
