package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.AccountTypeCert;
import com.atcpl.crowd.entity.Cert;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：CertService
 * @Date：2023/4/3 16:06
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface CertService {
    /**
     * 查询所有资质
     * @param pageSize 一页显示多少行
     * @param pageNumber 页数
     * @return
     */
    PageInfo<Cert> getAllCert(Integer pageSize, Integer pageNumber);

    /**
     * 添加资质
     * @param cert
     */
    void addCert(Cert cert);

    /**
     * 修改资质信息
     * @param cert
     */
    void editCert(Cert cert);

    /**
     * 删除资质信息
     * @param ids
     */
    void deleteCert(String ids);

    /**
     * 查询账户与资质的关系
     * @return
     */
    List<AccountTypeCert> getAccountTypeCerts();
}
