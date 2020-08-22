package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 用户
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User extends BaseEntity {
    private String email; // 电子邮箱

    private String username; // 用户账号

    private String password; // 密码

    private String headPic; // 头像

    private String phone; // 手机号

    private String personalitySignature; // 个性签名

    private int isBlog; // 博客是否开通

    private int acNumber;//通过题数

    private int submmitNumber;//提交次数
}
