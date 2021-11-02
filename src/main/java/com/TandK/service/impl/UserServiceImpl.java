package com.TandK.service.impl;

import com.TandK.core.cache.ObjectCacheKey;
import com.TandK.core.cache.RedisCache;
import com.TandK.core.exception.BusinessException;
import com.TandK.core.exception.BusinessExceptionEumn;
import com.TandK.core.jwt.JwtUtil;
import com.TandK.core.support.http.HttpResponseSupport;
import com.TandK.core.support.threadlocal.UserThreadLocal;
import com.TandK.mapper.UserMapper;
import com.TandK.model.po.UserPO;
import com.TandK.model.vo.LoginVO;
import com.TandK.model.vo.UserVO;
import com.TandK.service.UserService;
import com.TandK.service.UserTokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.TandK.core.support.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    @RedisCache(dynamicCacheKey = false, cacheKey = "users", cacheTimeout = 30, cacheTimeoutUnit = TimeUnit.SECONDS)
    @Override
    public ResponseEntity<Object> getUsers() {
        // 查询
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_delete", 0);
        List<UserPO> userPOS = userMapper.selectList(wrapper);
        // 如果查不到结果，直接抛异常
        if(userPOS.size() < 1){
            throw new BusinessException(BusinessExceptionEumn.RESULT_NOT_FOUND);
        }
        // 转化成vo
        List<UserVO> userVOS = userPOS.stream().map(userPO -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userPO, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return HttpResponseSupport.success(userVOS);
    }

    @Override
    public ResponseEntity<Object> addUser(UserVO userVO) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO, userPO);
        int rows = userMapper.insert(userPO);
        if(rows < 1){
            throw new BusinessException(BusinessExceptionEumn.ADD_ERROR);
        }
        return HttpResponseSupport.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> login(LoginVO loginVO) {
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
        return HttpResponseSupport.success(token);
    }

    @RedisCache(dynamicCacheKey = false, cacheKey = "mine", useDistributeLock = false)
    @Override
    public UserVO getMyInfo() {
        UserPO userPO = UserThreadLocal.get();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO, userVO);
        return userVO;
    }

    @RedisCache(dynamicCacheKeyPattern = "user_{}", dynamicCacheKeyParameterIndexArray = {0})
    @Override
    public UserVO getUser(String uuid) {
        UserPO userPO = userMapper.selectById(uuid);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO, userVO);
        return userVO;
    }
}
