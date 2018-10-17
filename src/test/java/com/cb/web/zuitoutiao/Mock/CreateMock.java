package com.cb.web.zuitoutiao.Mock;

import com.cb.web.zuitoutiao.dao.UserMapper;
import com.cb.web.zuitoutiao.service.impl.UserServiceImpl;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-10-17 10:33
 **/
class CreateMock {
    @Mock
    UserMapper mockUserMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        //初始化对象的注解
        MockitoAnnotations.initMocks(this);
    }
}
