package com.TandK.service.impl;

import com.TandK.core.exception.BizCodeEnum;
import com.TandK.core.exception.RRException;
import com.TandK.core.jwt.JwtUtil;
import com.TandK.core.support.threadlocal.UserThreadLocal;
import com.TandK.mapper.UserMapper;
import com.TandK.model.po.UserPO;
import com.TandK.model.vo.LoginVO;
import com.TandK.model.vo.UserVO;
import com.TandK.service.UserService;
import com.TandK.service.UserTokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TandK
 * @since 2021/9/8 22:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserTokenService userTokenService;


    @Override
    public List<UserVO> getUsers() {
        // 查询
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_delete", 0);
        List<UserPO> userPOS = userMapper.selectList(wrapper);
        // 如果查不到结果，直接抛异常
        if(userPOS.size() < 1){
            throw new RRException(BizCodeEnum.EMPTY_USER_LIST);
        }
        // 转化成vo
        List<UserVO> userVOS = userPOS.stream().map(userPO -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userPO, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return userVOS;
    }

    @Override
    public HttpStatus addUser(UserVO userVO) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO, userPO);
        int rows = userMapper.insert(userPO);
        if(rows < 1){
            throw new RRException(BizCodeEnum.ADD_USER_ERROR);
        }
        return HttpStatus.OK;
    }

    @Override
    public String login(LoginVO loginVO) {
        // 查询是否第一次登陆
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("account", loginVO.getAccount())
                .eq("password", loginVO.getPassword())
                .eq("is_delete", 0);
        UserPO userPO = userMapper.selectOne(wrapper);

        if(userPO == null){
            // 第一次登录，保存用户信息
            userPO = new UserPO();
            // 复制loginVO的账号密码的值到userPO
            BeanUtils.copyProperties(loginVO, userPO);
            // 保存用户信息
            userMapper.insert(userPO);
        }

        // 生成token信息
        String token = JwtUtil.createJWTBySecond(1000 * 60 * 60 * 24 * 30, userPO.getUuid());
        userTokenService.saveToken(userPO.getUuid(), token);
        return token;
    }


    @Override
    public UserVO getMyInfo() {
        UserPO userPO = UserThreadLocal.get();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO, userVO);
        return userVO;
    }


    @Override
    public UserVO getUser(String uuid) {
        UserPO userPO = userMapper.selectById(uuid);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO, userVO);
        return userVO;
    }
}
