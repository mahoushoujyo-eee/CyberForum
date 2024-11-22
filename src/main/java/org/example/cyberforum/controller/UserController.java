package org.example.cyberforum.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cyberforum.dto.ResetPasswordRequest;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController
{
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        logger.info("register user:" + JSON.toJSONString(user));
        return userService.addUser(user);
    }

    @PostMapping("/logIn")
    public boolean login(@RequestBody User user, HttpServletResponse response)
    {
        logger.info("login user:" + JSON.toJSONString(user));
        if (userService.login(user))
        {
            logger.info("login success: " + user.getUserName() + userService.getUserIdByUserName(user.getUserName()));
            response.addCookie(new Cookie("username", user.getUserName()));
            response.addCookie(new Cookie("userId", userService.getUserIdByUserName(user.getUserName()).toString()));
            logger.info("add cookie user id: " + userService.getUserIdByUserName(user.getUserName()));
            return true;
        }
        else
        {
            logger.info("login fail");
            return false;
        }
    }

    @PostMapping("/find_password")
    public boolean findPassword(@RequestBody ResetPasswordRequest user)
    {
        logger.info("find password user:" + JSON.toJSONString(user));
        return userService.resetPassword(user);
    }
}
