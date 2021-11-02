package com.TandK.service;

import com.TandK.model.po.UserPO;

/**
 * @author TandK
 * @since 2021/9/29 21:57
 */
public interface UserTokenService {

    /**
     * 通过token获取用户
     * @param token
     * @return
     */
    UserPO getUserByToken(String token);

    /**
     * 保存token
     * @param uuid
     * @param token
     */
    void saveToken(String uuid, String token);
}
