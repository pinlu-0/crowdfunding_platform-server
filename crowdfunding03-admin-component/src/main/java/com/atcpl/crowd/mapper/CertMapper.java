package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.Cert;
import com.atcpl.crowd.entity.CertExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：CertHandler
 * @Date：2023/4/3 15:51
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述此类)
 */
public interface CertMapper {
    /**
     * 查询所有资质
     * @return
     */
    List<Cert> getAllCert();

    int countByExample(CertExample example);

    int deleteByExample(CertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    int insertSelective(Cert record);

    List<Cert> selectByExample(CertExample example);

    Cert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cert record, @Param("example") CertExample example);

    int updateByExample(@Param("record") Cert record, @Param("example") CertExample example);

    int updateByPrimaryKeySelective(Cert record);

    int updateByPrimaryKey(Cert record);
}