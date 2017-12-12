package com.eid.anonymous;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基础类
 * Created by:ruben Date:16/7/28 Time:下午5:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dubbo-provider.xml")
public class BaseServiceTest {
}
