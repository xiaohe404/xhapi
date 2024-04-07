package com.xiaohe.xhapibackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户签到
 * @TableName user_check_in
 */
@TableName(value ="user_check_in")
@Data
public class UserCheckIn implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 签到人
     */
    private Long userId;

    /**
     * 描述
     */
    private String description;

    /**
     * 签到增加积分个数
     */
    private Long addPoints;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}