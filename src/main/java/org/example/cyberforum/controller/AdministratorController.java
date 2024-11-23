package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.Administrator;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class  AdministratorController
{

    @Autowired
    AdministratorService administratorService;

    // region functions for ...

    @GetMapping("/get_administrator/{forum_id}")
    public List<User> getAdministrator(@PathVariable("forum_id") Long forumId)
    {
        List<User> administrators = administratorService.getAdministratorByForumId(forumId);
        return administrators;
    }

    @PostMapping("/add_administrator")
    public String addAdministrator(@RequestBody Administrator administrator)
    {
        return administratorService.addAdministrator(administrator.getForumId(), administrator.getUserName());
    }

    // endregion functions for ...

    @DeleteMapping("/delete_administrator/{forum_id}")
    public boolean deleteAdministrator(@RequestBody Administrator administrator)
    {
        return administratorService.deleteAdministrator(administrator.getForumId(), administrator.getUserId());
    }

    @RequestMapping("/is_administrator/{blog_id}")
    public boolean ifAdministratorFromBlog(@PathVariable("blog_id") Long blogId, @RequestBody User user)
    {
        boolean result = administratorService.ifAdministratorFromBlog(blogId, user.getId());
        log.info("result = " + result);
        return result;
    }

    @PostMapping("/is_administrator_by_forum_id/{forum_id}")
    public boolean ifAdministratorFromForum(@RequestBody Administrator administrator)
    {
        log.info("administrator = " + administrator);
        boolean result = administratorService.ifAdministratorFromForum(administrator.getUserId(), administrator.getForumId());
        log.info("result = " + result);
        return result;
    }
}
