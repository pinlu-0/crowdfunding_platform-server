package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.Advertise;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：AdvertiseService
 * @Date：2023/4/10 16:15
 * @Version：1.0.0
 * @Description TODO(广告业务逻辑层接口)
 */
public interface AdvertiseService {

    /**
     * 添加广告
     *
     * @param advertise
     */
    void addAdvertise(Advertise advertise);

    /**
     * 查询所有的广告
     * @return
     */
    List<Advertise> getAll();

}
