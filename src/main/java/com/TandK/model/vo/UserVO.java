package com.TandK.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author TandK
 * @since 2021/9/6 23:58
 */
@Data
public class UserVO {

    /**
     * 主键
     */
    private String uuid;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty("手机号")
    @Length(min = 11, max = 11, message = "手机号码只能为11")
    private String phone;

    @ApiModelProperty("年龄")
    @Min(value = 0, message = "年龄最小为0")
    @Max(value = 100, message = "年龄最大为100")
    private Integer age;

}
