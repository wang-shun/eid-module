package com.eid.dal.dao;

import com.eid.dal.entity.CertificateAuthenticationEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CertificateAuthenticationDao extends CrudRepository<CertificateAuthenticationEntity, Long> {

}
