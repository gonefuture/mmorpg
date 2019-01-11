package com.wan37.gameserver.game.trade.dao;

import com.wan37.mysql.pojo.entity.TAuctionItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/11 15:40
 * @version 1.00
 * Description: mmorpg
 */
@Mapper
public interface AuctionItemDao {


    @InsertProvider(type = AuctionSqlProvider.class, method = "insertAndReturnKey" )
    @Options(useGeneratedKeys=true, keyProperty="auctionId", keyColumn="auction_id")
    void insertAndReturnKey(TAuctionItem tAuctionItem);




}
