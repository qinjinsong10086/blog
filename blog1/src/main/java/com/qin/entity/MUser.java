package com.qin.entity;


import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author qin
 * @since 2020-11-17
 */
@Data
public class MUser {

    private static final long serialVersionUID = 1L;
    @TableId(value="id")
    private Integer id;

    @NotBlank(message="用户名不能为空")
    private String username;

    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String password;

    private Integer status;

    private LocalDateTime created;

    private LocalDateTime lastLogin;


}
