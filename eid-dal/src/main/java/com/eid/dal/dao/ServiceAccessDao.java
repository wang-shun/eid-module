package com.eid.dal.dao;

import com.eid.dal.entity.ServiceAccessEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/27 Time:下午4:45
 */
public interface ServiceAccessDao extends CrudRepository<ServiceAccessEntity, Long> {

    ServiceAccessEntity findByAccessId(String accessId);
}
