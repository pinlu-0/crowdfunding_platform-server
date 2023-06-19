package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.AccountType;
import com.atcpl.crowd.entity.AccountTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountTypeMapper {
    int countByExample(AccountTypeExample example);

    int deleteByExample(AccountTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountType record);

    int insertSelective(AccountType record);

    List<AccountType> selectByExample(AccountTypeExample example);

    AccountType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountType record, @Param("example") AccountTypeExample example);

    int updateByExample(@Param("record") AccountType record, @Param("example") AccountTypeExample example);

    int updateByPrimaryKeySelective(AccountType record);

    int updateByPrimaryKey(AccountType record);

    /**
     * 查询所有账户类型
     * @return
     */
    List<AccountType> getAllAccountType();

}