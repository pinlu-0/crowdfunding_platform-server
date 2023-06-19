package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.TMember;
import com.atcpl.crowd.entity.TMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberMapper {
    int countByExample(TMemberExample example);

    int deleteByExample(TMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMember record);

    int insertSelective(TMember record);

    List<TMember> selectByExample(TMemberExample example);

    TMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByExample(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByPrimaryKeySelective(TMember record);

    int updateByPrimaryKey(TMember record);


    TMember selectMemberInfoByTicket(String id);
}