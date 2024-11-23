package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.ResetPasswordRequest;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.mapper.UserMapper;
import org.example.cyberforum.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService
{
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public String addUser(User user)
    {
        String userName = user.getUserName();
        String password = user.getPassword();
        String userEmail = user.getEmail();

        if (ifContainsUserName(userName))
            return "false";

        String result = passwordValidMessageOf(password);
        if (!result.equals("true"))
            return result;

        if (!isEmailValid(userEmail))
            return "invalid_email";

        String encryptedPassword = MD5.hash(password);
        log.info("encryptedPassword: %s".formatted(encryptedPassword));
        userMapper.addUser(userName, encryptedPassword, userEmail);

        return "true";
    }

    public boolean login(User user)
    {
        // SELECT COUNT(*) FROM user WHERE password = #{} AND username = #{}
        String encryptedPassword = MD5.hash(user.getPassword());
        log.info("encryptedPassword: " + encryptedPassword);
        String dbPassword = userMapper.getEncryptedPassword(user.getUserName());
        return dbPassword.equals(encryptedPassword);
    }

    @Transactional(rollbackFor = Exception.class)
    // ResetPasswordRequest
    public boolean resetPassword(ResetPasswordRequest request)
    {
        if(!passwordValidMessageOf(request.getPassword()).equals("true"))
            return false;

        User user = userMapper.getUserByUserName(request.getUserName());
        if (user != null)
        {
            String email = user.getEmail();
            if (email.equals(request.getEmail()))
            {
                String encryptedPassword = MD5.hash(request.getPassword());
                userMapper.updateUser(request.getUserName(), encryptedPassword);
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
        // SELECT COUNT(*) FROM user WHERE username = #{userName}
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
        // 作为类的静态常量

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
