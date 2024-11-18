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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService
{
    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public String addUser(User user)
    {
        if (ifContainsUserName(user.getUserName()))
            return "false";

        String result = passwordValidMessageOf(user.getPassword());
        if (!result.equals("true"))
            return result;

        if (!isEmailValid(user.getEmail()))
            return "invalid_email";

        String encryptedPassword = MD5.hash(user.getPassword());
        log.info("encryptedPassword:" + encryptedPassword);
        userMapper.addUser(user.getUserName(), encryptedPassword, user.getEmail());

        return "true";
    }

    public boolean login(User user)
    {
        String encryptedPassword = MD5.hash(user.getPassword());
        log.info("encryptedPassword:" + encryptedPassword);
        String dbPassword = userMapper.getEncryptedPassword(user.getUserName());
        return dbPassword.equals(encryptedPassword);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean findPassword(User user)
    {
        if(!passwordValidMessageOf(user.getPassword()).equals("true"))
            return false;



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

            return true;
        }

        return false;
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

    public boolean ifContainsUser(Long userId)
    {
        return userMapper.getUserById(userId) != null;
    }

    public boolean ifContainsUserName(String userName)
    {
        return userMapper.getUserByUserName(userName) != null;
    }

    public String passwordValidMessageOf(String password)
    {
        if (password.trim().isBlank())
            return "null";
        else if (password.length() < 8)
            return "short";
        else if (password.length() > 16)
            return "long";
        else if (password.contains(" "))
            return "space";
        else if (!isValidPassword(password))
            return "invalid";
        else
            return "true";
    }

    public boolean isValidPassword(String password)
    {
        String regex = "^[a-zA-Z0-9]+$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(password);

        // 使用 Matcher 的 matches 方法检查整个区域
        return matcher.matches();
    }

    public boolean isEmailValid(String email)
    {
        return email.contains("@");
    }
}
