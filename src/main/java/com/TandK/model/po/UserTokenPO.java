package com.TandK.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_token
 */
@TableName(value ="user_token")
@Data
public class UserTokenPO implements Serializable {
    /**
     * 
     */
    @TableId(value = "uuid")
    private Long uuid;

    /**
     * 
     */
    @TableField(value = "user_uuid")
    private String userUuid;

    /**
     * 
     */
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 过期时间
     */
    @TableField(value = "expire_time")
    private Date expireTime;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}