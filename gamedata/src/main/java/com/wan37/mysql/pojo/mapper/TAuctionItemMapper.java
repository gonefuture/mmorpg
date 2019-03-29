package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TAuctionItem;
import com.wan37.mysql.pojo.entity.TAuctionItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAuctionItemMapper {
    int countByExample(TAuctionItemExample example);

    int deleteByExample(TAuctionItemExample example);

    int deleteByPrimaryKey(Integer auctionId);

    int insert(TAuctionItem record);

    int insertSelective(TAuctionItem record);

    List<TAuctionItem> selectByExample(TAuctionItemExample example);

    TAuctionItem selectByPrimaryKey(Integer auctionId);

    int updateByExampleSelective(@Param("record") TAuctionItem record, @Param("example") TAuctionItemExample example);

    int updateByExample(@Param("record") TAuctionItem record, @Param("example") TAuctionItemExample example);

    int updateByPrimaryKeySelective(TAuctionItem record);

    int updateByPrimaryKey(TAuctionItem record);
}