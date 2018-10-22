package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TBuffer;
import com.wan37.mysql.pojo.entity.TBufferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBufferMapper {
    int countByExample(TBufferExample example);

    int deleteByExample(TBufferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBuffer record);

    int insertSelective(TBuffer record);

    List<TBuffer> selectByExample(TBufferExample example);

    TBuffer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBuffer record, @Param("example") TBufferExample example);

    int updateByExample(@Param("record") TBuffer record, @Param("example") TBufferExample example);

    int updateByPrimaryKeySelective(TBuffer record);

    int updateByPrimaryKey(TBuffer record);
}