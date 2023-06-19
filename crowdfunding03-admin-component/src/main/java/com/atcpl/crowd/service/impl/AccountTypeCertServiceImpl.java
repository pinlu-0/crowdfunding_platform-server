package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.AccountTypeCert;
import com.atcpl.crowd.entity.AccountTypeCertExample;
import com.atcpl.crowd.mapper.AccountTypeCertMapper;
import com.atcpl.crowd.service.api.AccountTypeCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：AccountTypeCertServiceImpl
 * @Date：2023/4/6 20:43
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service
public class AccountTypeCertServiceImpl implements AccountTypeCertService {

    @Autowired
    AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public void checkAcctCertRelation(Integer certId, Integer acctId) {
        accountTypeCertMapper.insert(new AccountTypeCert(acctId,certId));
    }

    @Override
    public List<AccountTypeCert> relationExist(Integer certId, Integer acctId) {
        AccountTypeCertExample example = new AccountTypeCertExample();
        AccountTypeCertExample.Criteria criteria = example.createCriteria();
        criteria.andCertidEqualTo(certId);
        criteria.andAccttypeidEqualTo(acctId);
        return accountTypeCertMapper.selectByExample(example);
    }

    @Override
    public void removeAcctCertRelation(Integer certId, Integer acctId) {
        AccountTypeCertExample example = new AccountTypeCertExample();
        AccountTypeCertExample.Criteria criteria = example.createCriteria();
        criteria.andCertidEqualTo(certId);
        criteria.andAccttypeidEqualTo(acctId);
        accountTypeCertMapper.deleteByExample(example);
    }
}
