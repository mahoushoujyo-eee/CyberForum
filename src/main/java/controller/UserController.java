package controller;

import bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @PostMapping("/register")
    public String register(@RequestParam("username") String userName, @RequestParam("password") String password, @RequestParam("email") String email)
    {
        System.out.println("register");
        return "register";
    }
}
