package com.lelefans.mwy.util;

import com.lelefans.mwy.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;
import sun.rmi.runtime.Log;

import java.util.Date;

@Slf4j
public class LoginUtil {
    private static final String spliter = "#_`";
    private static final String myKey = "lele!@123";

    /**
     * 加密用户信息
     *
     * @param userId
     * @param loginId
     * @param name
     * @param expireDate
     * @return
     */
    public static String encode(int userId, String loginId, String name, Date expireDate) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId).append(spliter)
                .append(loginId).append(spliter)
                .append(name).append(spliter)
                .append(expireDate.getTime());
        String s = builder.toString();
        byte[] bytes = s.getBytes();
        byte[] secret = myKey.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] ^= secret[i % secret.length];
        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 解密用户信息
     *
     * @param token
     * @return
     */
    public static UserModel decoder(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        byte[] bytes = Base64.decodeBase64(token);
        byte[] secret = myKey.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] ^= secret[i % secret.length];
        }
        String s = new String(bytes);
        String[] split = s.split(spliter);
        if (split.length != 4 || Long.valueOf(split[3]) < new Date().getTime()) {
            return null;
        }
        UserModel model = new UserModel();
        model.setId(Integer.valueOf(split[0]));
        model.setLoginId(split[1]);
        model.setName(split[2]);
        model.setToken(token);
        return model;
    }
}
