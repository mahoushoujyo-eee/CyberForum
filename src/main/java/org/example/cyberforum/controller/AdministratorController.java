package org.example.cyberforum.controller;

import org.example.cyberforum.bean.Administrator;
import org.example.cyberforum.bean.User;
import org.example.cyberforum.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class  AdministratorController
{

    @Autowired
    AdministratorService administratorService;

    @RequestMapping("/get_administrator/{id}")
    public List<User> getAdministrator(@PathVariable("id") Long forumId)
    {
        List<User> administrators = administratorService.getAdministratorByForumId(forumId);
        return administrators;
    }

    @RequestMapping("/add_administrator/{forumId}")
    public void addAdministrator(@PathVariable("forumId") Long forumId, @RequestBody User user)
    {
        administratorService.addAdministrator(forumId, user.getUserName());
    }

    @RequestMapping("/delete_administrator/{forumId}")
    public void deleteAdministrator(@PathVariable("forumId") Long forumId, @RequestBody User user)
    {
        administratorService.deleteAdministrator(forumId, user.getUserName());
    }

}
