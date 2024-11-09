package org.example.cyberforum.service;

import org.example.cyberforum.bean.User;
import org.example.cyberforum.mapper.UserMapper;
import org.example.cyberforum.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean addUser(User user)
    {
        String encryptedPassword = MD5.hash(user.getPassword());
        logger.info("encryptedPassword:" + encryptedPassword);
        userMapper.addUser(user.getUserName(), encryptedPassword, user.getEmail());
        return true;
    }

    public boolean login(User user)
    {
        String encryptedPassword = MD5.hash(user.getPassword());
        logger.info("encryptedPassword:" + encryptedPassword);
        String dbPassword = userMapper.getEncryptedPassword(user.getUserName());
        if (dbPassword.equals(encryptedPassword))
            return true;
        return false;
    }

    public boolean findPassword(User user)
    {
        User sqlUser = userMapper.getUserByUserName(user.getUserName());
        if (sqlUser != null)
        {
            String email = sqlUser.getEmail();
            if (email.equals(user.getEmail()))
            {
                String encryptedPassword = MD5.hash(user.getPassword());
                userMapper.updateUser(user.getUserName(), encryptedPassword);
                return true;
            }
            logger.info("email:" + email);
        }

        return false;
    }
}
