package com.lelefans.mwy.service.game.kdou.impl;

import com.lelefans.mwy.dao.Entity.UserEntity;
import com.lelefans.mwy.dao.UserDao;
import com.lelefans.mwy.model.UserModel;
import com.lelefans.mwy.service.game.kdou.LoginService;
import com.lelefans.mwy.util.DateUtil;
import com.lelefans.mwy.util.LoginUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private final static String CommonUser = "common_";
    private final static String DEFAULT_PWD = "common_";
    @Resource
    private UserDao userDao;
    private Map<String, UserModel> userMap = new HashMap<>();

    @Override
    public UserModel login(String token) {
        return LoginUtil.decoder(token);
    }

    @Override
    @Transactional
    public UserModel login(String loginId, String pwd) {
        if (CommonUser.equals(loginId)) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName("游客");
            userEntity.setPwd(DEFAULT_PWD);
            userDao.insertUser(userEntity);
            userEntity.setLoginId(userEntity.getId() + "_" + ((int) Math.random() * 1000));
            userDao.updateUser(userEntity);
            return toUserModel(userEntity);
        } else {
            UserEntity user = userDao.getUserByLoginId(loginId);
            if (user != null && StringUtils.equals(user.getPwd(),pwd)){
                return toUserModel(user);
            }else {
                return null;
            }
        }
    }

    private UserModel toUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setToken(LoginUtil.encode(userEntity.getId(), userEntity.getLoginId(), userEntity.getName(), DateUtil.getDateAfter(new Date(), 180)));
        userModel.setName(userEntity.getName());
        userModel.setLoginId(userEntity.getLoginId());
        userModel.setId(userEntity.getId());
        return userModel;
    }
}
