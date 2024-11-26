package org.example.cyberforum.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.ResetPasswordRequest;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;

@Slf4j
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ServiceResponse<Boolean> register(@RequestBody User user)
    {
        log.info("register user:" + JSON.toJSONString(user));
        return userService.addUser(user);
    }

    @PostMapping("/logIn")
    public boolean login(@RequestBody User user, HttpServletResponse response)
    {
        log.info("login user:{}", JSON.toJSONString(user));
        if (userService.login(user).getData())
        {
            log.info("login success: {}{}", user.getUserName(), userService.getUserIdByUserName(user.getUserName()));
            response.addCookie(new Cookie("username", user.getUserName()));
            response.addCookie(new Cookie("userId", userService.getUserIdByUserName(user.getUserName()).toString()));
            log.info("add cookie user id: {}", userService.getUserIdByUserName(user.getUserName()));
            return true;
        }
        else
        {
            log.info("login fail");
            return false;
        }
    }

    @PostMapping("/find_password")
    public ServiceResponse<Boolean> findPassword(@RequestBody ResetPasswordRequest user)
    {
        log.info("find password user:" + JSON.toJSONString(user));
        return userService.resetPassword(user);
    }
}
