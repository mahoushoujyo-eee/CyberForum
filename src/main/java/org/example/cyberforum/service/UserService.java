package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.ResetPasswordRequest;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.mapper.UserMapper;
import org.example.cyberforum.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@LogArgumentsAndResponse
public class UserService
{
    @Autowired
    private UserMapper userMapper;
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addUser(User user)
    {
        String userName = user.getUserName();
        String password = user.getPassword();
        String userEmail = user.getEmail();

        if (ifContainsUserName(userName))
            return ServiceResponse.buildErrorResponse(-100, "username exists");

        String result = passwordValidMessageOf(password);
        if (!result.equals("true"))
            return ServiceResponse.buildErrorResponse(-100, result);

        if (!isEmailValid(userEmail))
            return ServiceResponse.buildErrorResponse(-100, "invalid email");

        String encryptedPassword = MD5.hash(password);
        userMapper.addUser(userName, encryptedPassword, userEmail);

        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<Boolean> login(User user)
    {
        if (!ifContainsUserName(user.getUserName()))
            return ServiceResponse.buildErrorResponse(-100, "username not exists");
        String encryptedPassword = MD5.hash(user.getPassword());

        return ServiceResponse.buildSuccessResponse(userMapper.ifUserNameAndEncryptedPasswordMatch(user.getUserName(), encryptedPassword));
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> resetPassword(ResetPasswordRequest request)
    {
        String result = passwordValidMessageOf(request.getPassword());
        if(!result.equals("true"))
            return ServiceResponse.buildErrorResponse(-100, result);

        if (!userMapper.ifUserNameANdEmailMatch(request.getUserName(), request.getEmail()))
            return ServiceResponse.buildErrorResponse(-100, "username and email not match");

        String encryptedPassword = MD5.hash(request.getPassword());
        userMapper.updateUser(request.getUserName(), encryptedPassword);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<Long> getUserIdByUserName(String userName)
    {
        if (!ifContainsUserName(userName))
            return ServiceResponse.buildErrorResponse(-100, "username not exists");
        return ServiceResponse.buildSuccessResponse(userMapper.getUserIdByUserName(userName));
    }

    public boolean ifContainsUser(Long userId)
    {
        return userMapper.getUserById(userId) != null;
    }

    public boolean ifContainsUserName(String userName)
    {
        return userMapper.ifContainsUserByUserName(userName);
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
        // 创建 Matcher 对象
        Matcher matcher = PATTERN.matcher(password);
        // 使用 Matcher 的 matches 方法检查整个区域
        return matcher.matches();
    }

    public boolean isEmailValid(String email)
    {
        return email.contains("@");
    }
}
