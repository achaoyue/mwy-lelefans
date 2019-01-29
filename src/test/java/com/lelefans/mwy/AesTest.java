package com.lelefans.mwy;

import com.lelefans.mwy.util.AESUtil;

public class AesTest {
    public static void main(String[] args) throws Exception {
        String encode = AESUtil.encrypt("this is test", "123");
        String decode = AESUtil.decrypt(encode, "123");

        System.out.println(new String(decode));
    }
}
