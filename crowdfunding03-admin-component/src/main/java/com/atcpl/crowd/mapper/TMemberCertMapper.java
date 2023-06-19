package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.TMemberCert;
import com.atcpl.crowd.entity.TMemberCertExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMemberCertMapper {
    int countByExample(TMemberCertExample example);

    int deleteByExample(TMemberCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberCert record);

    int insertSelective(TMemberCert record);

    List<TMemberCert> selectByExample(TMemberCertExample example);

    TMemberCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberCert record, @Param("example") TMemberCertExample example);

    int updateByExample(@Param("record") TMemberCert record, @Param("example") TMemberCertExample example);

    int updateByPrimaryKeySelective(TMemberCert record);

    int updateByPrimaryKey(TMemberCert record);

    List<TMemberCert> selectCertsByTicketId(String processInstanceId);

}