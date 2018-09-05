package com.cb.web.zuitoutiao.service;

import java.io.IOException;

/**
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-24 13:42
 **/
public interface UserReadService {
    void insertUserRead(Integer userId, Integer articleId) throws IOException;
}
