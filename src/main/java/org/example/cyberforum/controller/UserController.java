package org.example.cyberforum.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.cyberforum.bean.User;
import org.example.cyberforum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController
{
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public boolean register(@RequestBody User user)
    {
        logger.info("register user:" + JSON.toJSONString(user));
        userService.addUser(user);
        return true;
    }

    @RequestMapping("/logIn")
    @ResponseBody
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

    @RequestMapping("/find_password")
    @ResponseBody
    public boolean findPassword(@RequestBody User user)
    {
        logger.info("find password user:" + JSON.toJSONString(user));
        return userService.findPassword(user);
    }
}
