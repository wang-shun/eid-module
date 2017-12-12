package com.eid.identity.biz;

import com.eid.dal.dao.BizCmdDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.identity.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/19 Time:下午7:11
 */
public class DemoBizTest extends BaseServiceTest {

    @Autowired
    private BizCmdDao bizCmdDao;

    @Test
    public void cmdTest() {
        PageRequest pageRequest = new PageRequest(0, 50, new Sort(Sort.Direction.ASC, "updatedAt"));
        Page<BizCmdEntity> page = bizCmdDao.selectToDoCmdList("ASYNC_FEE", "dev", pageRequest);
        for (BizCmdEntity cmd : page) {
            System.out.println(cmd);
        }
    }

}
