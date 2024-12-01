package org.example.cyberforum.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.ResetPasswordRequest;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.service.UserService;
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
        return userService.addUser(user);
    }

    @PostMapping("/logIn")
    public ServiceResponse<Boolean> login(@RequestBody User user, HttpServletResponse response)
    {
        ServiceResponse<Boolean> result = userService.login(user);
        if (result.isSuccess() && result.getData())
        {
            response.addCookie(new Cookie("username", user.getUserName()));
            response.addCookie(new Cookie("userId", userService.getUserIdByUserName(user.getUserName()).getData().toString()));
        }
        return result;
    }

    @PostMapping("/find_password")
    public ServiceResponse<Boolean> findPassword(@RequestBody ResetPasswordRequest user)
    {
        return userService.resetPassword(user);
    }
}
