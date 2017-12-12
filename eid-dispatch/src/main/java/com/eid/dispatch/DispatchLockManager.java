package com.eid.dispatch;

import com.eid.dal.dao.BizLockDao;
import com.eid.dal.entity.BizLockEntity;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:39
 */
@Slf4j
@Component
public class DispatchLockManager {

    @Autowired
    private BizLockDao bizLockDao;

    /**
     * 获取业务锁
     *
     * @param lockName 锁名
     * @return 获取成功 return true; 获取失败 return false;
     */
    public boolean getLockNoWait(String lockName) {
        try {
            BizLockEntity lockResult = bizLockDao.findByLockNameAndStatus(lockName, "1");
            if (Objects.equal(lockResult, null)) {
                BizLockEntity bizLockDO = new BizLockEntity();
                bizLockDO.setLockName(lockName);
                bizLockDO.setCreatedAt(new Date());
                bizLockDO.setStatus("1");
                lockResult = bizLockDao.save(bizLockDO);
            }
            BizLockEntity result = bizLockDao.findById(lockResult.getId());
            if (result == null) {
                log.error("[业务锁控制器]--锁：[" + lockName + "]竞争失败");
                return false;
            }
        } catch (Exception e) {
            log.error("[业务锁控制器]--未竞争到锁：[" + lockName + "]");
            return false;
        }

        log.debug("[业务锁控制器]--锁：[" + lockName + "]竞争成功");
        return true;
    }
}