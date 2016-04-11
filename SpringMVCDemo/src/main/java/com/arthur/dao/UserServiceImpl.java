package com.arthur.dao;

import com.arthur.model.UserEntity;
import com.arthur.repository.UserRepository;
import com.google.code.ssm.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 12/16/2015.
 */
@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @ReadThroughSingleCache(namespace = "test", expiration = 30000)
    public UserEntity getUserByID(@ParameterValueKeyProvider Integer userId) {
        UserEntity userEntity = userRepository.findOne(userId);
        System.out.println("get user by ID,no cache");
        return userEntity;
    }

    public void saveUser(UserEntity userEntity) {
        // 数据库中添加一个用户，并立即刷新
        userRepository.saveAndFlush(userEntity);
    }

    @UpdateSingleCache(namespace = "test", expiration = 300)
    public void updateUser(@ParameterValueKeyProvider @ParameterDataUpdateContent UserEntity userEntity) {
        // 更新用户信息
        userRepository.updateUser(userEntity.getFirstName(), userEntity.getLastName(),
                userEntity.getPassword(), userEntity.getId());
    }

    @InvalidateSingleCache(namespace = "test")
    public void deleteUser(@ParameterValueKeyProvider Integer userId) {
        // 删除id为userId的用户
        userRepository.delete(userId);
        // 立即刷新
        userRepository.flush();
    }

    public List<UserEntity> getAllUser() {
        System.out.println("get all users,no cache");
        return userRepository.findAll();
    }
}
