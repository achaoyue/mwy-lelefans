package com.lelefans.mwy.service.game.kdou;

import com.lelefans.mwy.model.UserModel;

public interface LoginService {
    /**
     * 根据token登陆
     * @param token
     * @return
     */
    UserModel login(String token);

    /**
     * 用户名密码登陆
     * @param name
     * @param pwd
     * @return
     */
    UserModel login(String name,String pwd);

}
