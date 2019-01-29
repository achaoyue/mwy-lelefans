package com.lelefans.mwy.dao;

import com.lelefans.mwy.dao.Entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    /**
     * 通过loginid获取用户
     * @param loginId
     * @return
     */
    UserEntity getUserByLoginId(String loginId);

    int insertUser(UserEntity userEntity);

    int updateUser(UserEntity userEntity);
}
