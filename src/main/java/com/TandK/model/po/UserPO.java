package com.TandK.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserPO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "uuid")
    private String uuid;

    /**
     * 用户名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * password
     */
    @TableField(value = "password")
    private String password;

    /**
     * 1删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

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