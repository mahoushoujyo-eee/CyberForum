package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.User;

import java.util.List;

@Mapper
public interface UserMapper
{
    void addUser(String userName, String encryptedPassword, String email);

    String getEncryptedPassword(String userName);

    User getUserByUserName(String userName);

    void updateUser(String userName, String encryptedPassword);

    List<User> getUserList();
}
