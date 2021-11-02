package com.TandK.service.impl;

import cn.hutool.core.date.DateUtil;
import com.TandK.mapper.UserTokenMapper;
import com.TandK.model.po.UserPO;
import com.TandK.model.po.UserTokenPO;
import com.TandK.service.UserTokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author TandK
 * @since 2021/9/29 21:58
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {


    @Autowired
    private UserTokenMapper userTokenMapper;


    @Override
    public UserPO getUserByToken(String token) {
        return userTokenMapper.selectUserByToken(token);
    }

    @Override
    public void saveToken(String uuid, String token) {
        // 查出原先有用的token
        QueryWrapper<UserTokenPO> wrapper = new QueryWrapper();
        wrapper.eq("user_uuid", uuid)
                .orderByDesc("update_time")
                .last("LIMIT 1");
        UserTokenPO oldToken = userTokenMapper.selectOne(wrapper);

        Date nextMonth = DateUtil.nextMonth();
        if(oldToken == null){
            // 没有生成新的token
            UserTokenPO userTokenPO = new UserTokenPO();
            userTokenPO.setUserUuid(uuid);
            userTokenPO.setAccessToken(token);
            userTokenPO.setExpireTime(nextMonth);
            userTokenMapper.insert(userTokenPO);
            return;
        }

        // 有就更新
        oldToken.setAccessToken(token);
        oldToken.setExpireTime(nextMonth);
        userTokenMapper.updateById(oldToken);
    }
}
