package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TItem;
import com.wan37.mysql.pojo.entity.TItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TItemMapper {
    int countByExample(TItemExample example);

    int deleteByExample(TItemExample example);

    int deleteByPrimaryKey(String itemId);

    int insert(TItem record);

    int insertSelective(TItem record);

    List<TItem> selectByExample(TItemExample example);

    TItem selectByPrimaryKey(String itemId);

    int updateByExampleSelective(@Param("record") TItem record, @Param("example") TItemExample example);

    int updateByExample(@Param("record") TItem record, @Param("example") TItemExample example);

    int updateByPrimaryKeySelective(TItem record);

    int updateByPrimaryKey(TItem record);
}