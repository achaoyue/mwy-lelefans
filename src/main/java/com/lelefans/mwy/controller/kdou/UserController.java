package com.lelefans.mwy.controller.kdou;

import com.lelefans.mwy.model.ApiResponseModel;
import com.lelefans.mwy.model.UserModel;
import com.lelefans.mwy.service.game.kdou.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/game/user")
public class UserController {
    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    public ApiResponseModel<UserModel> login(String name, String pwd){
        return ApiResponseModel.success(loginService.login(name,pwd));
    }
}
