package com.lelefans.mwy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    /**
     * 本次登陆token
     */
    private String token;
    /**
     * 用户数据库id
     */
    private int id;
    /**
     * 用户id
     */
    private String loginId;
    /**
     * 用户名字
     */
    private String name;
}
