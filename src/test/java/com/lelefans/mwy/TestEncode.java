package com.lelefans.mwy;


import com.lelefans.mwy.model.UserModel;
import com.lelefans.mwy.util.LoginUtil;

import java.util.Date;

public class TestEncode {
    public static void main(String[] args) {
        String encode = LoginUtil.encode(123, "mwy", "mwy", new Date(new Date().getTime()+1000*10));

        System.out.println(encode);

        UserModel decoder = LoginUtil.decoder(encode);
        System.out.println(decoder);

        System.out.println("over");
    }
}
