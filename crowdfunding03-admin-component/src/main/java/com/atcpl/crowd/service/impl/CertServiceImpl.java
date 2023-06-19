package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.AccountTypeCert;
import com.atcpl.crowd.entity.Cert;
import com.atcpl.crowd.entity.CertExample;
import com.atcpl.crowd.mapper.AccountTypeCertMapper;
import com.atcpl.crowd.mapper.CertMapper;
import com.atcpl.crowd.service.api.CertService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：CertServiceImpl
 * @Date：2023/4/3 16:06
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service
public class CertServiceImpl implements CertService {

    @Autowired
    CertMapper certMapper;

    @Autowired
    AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public PageInfo<Cert> getAllCert(Integer pageSize, Integer pageNumber) {
        // 使用startPage 方法开始分页
        PageHelper.startPage(pageNumber,pageSize );
        // 开始查询
        List<Cert> allCert = certMapper.getAllCert();
        return new PageInfo<>(allCert);
    }

    @Override
    public void addCert(Cert cert) {
        certMapper.insert(cert);
    }

    @Override
    public void editCert(Cert cert) {
        certMapper.updateByPrimaryKey(cert);
    }

    @Override
    public void deleteCert(String ids) {
        if(ids.contains(",")){
            // 包含 , 就进行批量删除
            String[] split = ids.split(",");
            List<Integer> idsList = new ArrayList<>();
            for(String s : split){
                int i = Integer.parseInt(s);
                idsList.add(i);
            }
            CertExample example = new CertExample();
            CertExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(idsList);
            certMapper.deleteByExample(example);
        }else{
            // 不含, 说明是单个删除
            certMapper.deleteByPrimaryKey(Integer.parseInt(ids));
        }
    }

    @Override
    public List<AccountTypeCert> getAccountTypeCerts() {
        return accountTypeCertMapper.selectByExample(null);
    }
}
