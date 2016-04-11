package com.arthur.dao;

import com.arthur.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 12/15/2015.
 */
@Repository
public interface UserService {
    public List<UserEntity> getAllUser();

    public UserEntity getUserByID(Integer userId);

    public void saveUser(UserEntity userEntity);

    public void updateUser(UserEntity userEntity);

    public void deleteUser(Integer userId);
}
