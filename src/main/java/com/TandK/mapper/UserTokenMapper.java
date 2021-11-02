package com.TandK.mapper;

import com.TandK.model.po.UserPO;
import com.TandK.model.po.UserTokenPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author TandK
 * @since 2021/9/29 22:37
 */
public interface UserTokenMapper extends BaseMapper<UserTokenPO> {

    UserPO selectUserByToken(String token);
}
