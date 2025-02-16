package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.entities.User;

import java.util.List;

@Mapper
public interface UserMapper
{
    void addUser(String userName, String encryptedPassword, String email);

    String getEncryptedPassword(String userName);

    User getUserByUserName(String userName);

    void updateUser(String userName, String encryptedPassword);

    List<User> getUserList();

    Long getUserIdByUserName(String userName);

    User getUserById(Long userId);

    boolean containsUser(User user);
    boolean containsUserByUserName(String userName);
    boolean ifUserNameAndEncryptedPasswordMatch(String userName, String encryptedPassword);
    boolean ifUserNameANdEmailMatch(String userName, String email);
}
