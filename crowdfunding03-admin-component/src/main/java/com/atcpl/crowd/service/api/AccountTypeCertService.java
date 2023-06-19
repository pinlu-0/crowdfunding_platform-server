package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.AccountTypeCert;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：AccountTypeCertService
 * @Date：2023/4/6 20:41
 * @Version：1.0.0
 * @Description TODO(账户类型-资质中间表业务逻辑接口)
 */
public interface AccountTypeCertService {

    /**
     * 选择账户类型与资质的关系
     * @param certId
     * @param acctId
     */
    void checkAcctCertRelation(Integer certId, Integer acctId);

    /**
     * 判断关系是否已经存在
     * @param certId
     * @param acctId
     * @return
     */
    List<AccountTypeCert> relationExist(Integer certId, Integer acctId);

    /**
     * 删除选中的关系
     * @param certId
     * @param acctId
     */
    void removeAcctCertRelation(Integer certId, Integer acctId);
}
