package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TBag;
import com.wan37.mysql.pojo.entity.TBagExample;
import com.wan37.mysql.pojo.entity.TBagKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBagMapper {
    int countByExample(TBagExample example);

    int deleteByExample(TBagExample example);

    int deleteByPrimaryKey(TBagKey key);

    int insert(TBag record);

    int insertSelective(TBag record);

    List<TBag> selectByExampleWithBLOBs(TBagExample example);

    List<TBag> selectByExample(TBagExample example);

    TBag selectByPrimaryKey(TBagKey key);

    int updateByExampleSelective(@Param("record") TBag record, @Param("example") TBagExample example);

    int updateByExampleWithBLOBs(@Param("record") TBag record, @Param("example") TBagExample example);

    int updateByExample(@Param("record") TBag record, @Param("example") TBagExample example);

    int updateByPrimaryKeySelective(TBag record);

    int updateByPrimaryKeyWithBLOBs(TBag record);

    int updateByPrimaryKey(TBag record);
}