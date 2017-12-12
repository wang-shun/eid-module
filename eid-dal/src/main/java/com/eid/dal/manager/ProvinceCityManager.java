package com.eid.dal.manager;

import com.eid.dal.entity.ProvinceCityEntity;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/21 Time:下午3:42
 */
public interface ProvinceCityManager {

    /**
     * 获取身份证前六位地区码
     *
     * @param code
     * @return
     */
    ProvinceCityEntity queryCity(Integer code);
}
