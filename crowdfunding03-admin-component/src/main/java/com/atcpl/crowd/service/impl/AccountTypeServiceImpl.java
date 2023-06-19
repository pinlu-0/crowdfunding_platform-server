package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.AccountType;
import com.atcpl.crowd.mapper.AccountTypeMapper;
import com.atcpl.crowd.service.api.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：AccountTypeServiceImpl
 * @Date：2023/4/5 17:27
 * @Version：1.0.0
 * @Description TODO(账户类型业务逻辑几口实现层)
 */
@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountType> getAllAccountType() {
        return accountTypeMapper.getAllAccountType();
    }


}
