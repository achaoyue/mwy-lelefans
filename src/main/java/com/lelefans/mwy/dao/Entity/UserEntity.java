package com.lelefans.mwy.dao.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {
    private int id;
    private String loginId;
    private String pwd;
    private String name;
    private String mail;
    private String phone;
    private String createIp;
    private Date createTime;
}
