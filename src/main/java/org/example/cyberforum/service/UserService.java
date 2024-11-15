package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.bean.User;
import org.example.cyberforum.mapper.UserMapper;
import org.example.cyberforum.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService
{
    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user)
    {
        String encryptedPassword = MD5.hash(user.getPassword());
        log.info("encryptedPassword:" + encryptedPassword);
        userMapper.addUser(user.getUserName(), encryptedPassword, user.getEmail());
    }

    public boolean login(User user)
    {
        String encryptedPassword = MD5.hash(user.getPassword());
        log.info("encryptedPassword:" + encryptedPassword);
        String dbPassword = userMapper.getEncryptedPassword(user.getUserName());
        return dbPassword.equals(encryptedPassword);
    }

    public void findPassword(User user)
    {
        User sqlUser = userMapper.getUserByUserName(user.getUserName());
        if (sqlUser != null)
        {
            String email = sqlUser.getEmail();
            if (email.equals(user.getEmail()))
            {
                String encryptedPassword = MD5.hash(user.getPassword());
                userMapper.updateUser(user.getUserName(), encryptedPassword);
            }
            log.info("email:" + email);
        }
    }

    public Long getUserIdByUserName(String userName)
    {
        log.info("userName:" + userName);
        return userMapper.getUserIdByUserName(userName);
    }

    public User getUserById(Long userId)
    {
        return userMapper.getUserById(userId);
    }
}
