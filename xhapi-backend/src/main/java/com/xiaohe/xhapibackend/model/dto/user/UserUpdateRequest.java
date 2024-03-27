package com.xiaohe.xhapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 */
@Data
public class UserUpdateRequest implements Serializable {

    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别 0-男 1-女
     */
    private String gender;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 用户积分,注册送30点
     */
    private Long points;

    private static final long serialVersionUID = 1L;
}