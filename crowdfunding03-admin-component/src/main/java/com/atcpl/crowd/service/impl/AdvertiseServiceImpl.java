package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.Advertise;
import com.atcpl.crowd.mapper.AdvertiseMapper;
import com.atcpl.crowd.service.api.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：AdvertiseServiceImpl
 * @Date：2023/4/10 16:15
 * @Version：1.0.0
 * @Description TODO(广告业务逻辑层)
 */
@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    AdvertiseMapper advertiseMapper;

    @Override
    public void addAdvertise(Advertise advertise) {
        advertiseMapper.insertSelective(advertise);
    }

    @Override
    public List<Advertise> getAll() {
        return advertiseMapper.selectByExample(null);
    }


}
