package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.AccountType;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：AccountTypeService
 * @Date：2023/4/5 17:27
 * @Version：1.0.0
 * @Description TODO(账户类型业务逻辑接口)
 */
public interface AccountTypeService {

    /**
     * 查询所有的账户类型
     * @return
     */
    List<AccountType> getAllAccountType();

}
