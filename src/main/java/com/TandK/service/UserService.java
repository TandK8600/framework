package com.TandK.service;

import com.TandK.model.vo.LoginVO;
import com.TandK.model.vo.UserVO;
import com.TandK.core.support.http.ResponseEntity;

import java.util.List;

/**
 * @author TandK
 * @since 2021/9/8 22:07
 */
public interface UserService {

    /**
     * 获取用户列表
     * @return
     */
    ResponseEntity<Object> getUsers();

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    ResponseEntity<Object> addUser(UserVO userVO);

    /**
     * 登录
     * @param loginVO
     * @return
     */
    ResponseEntity<Object> login(LoginVO loginVO);

    /**
     * 获取登录用户的信息
     * @return
     */
    UserVO getMyInfo();

    /**
     * 获取指定用户的信息
     * @param uuid
     * @return
     */
    UserVO getUser(String uuid);
}
