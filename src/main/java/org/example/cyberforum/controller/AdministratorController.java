package org.example.cyberforum.controller;

import org.example.cyberforum.bean.Administrator;
import org.example.cyberforum.bean.User;
import org.example.cyberforum.service.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class  AdministratorController
{

    @Autowired
    AdministratorService administratorService;

    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

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

    @RequestMapping("/is_administrator/{blog_id}")
    public boolean judgeAdministrator(@PathVariable("blog_id") Long blogId, @RequestBody User user)
    {
        boolean result = administratorService.ifAdministrator(blogId, user.getId());
        logger.info("result = " + result);
        return result;
    }



    @PostMapping("/is_administrator_by_forum_id/{forum_id}")
    public boolean isAdministrator(@RequestBody Administrator administrator)
    {
        logger.info("administrator = " + administrator);
        boolean result = administratorService.isAdministrator(administrator.getUserId(), administrator.getForumId());
        logger.info("result = " + result);
        return result;
    }


}
