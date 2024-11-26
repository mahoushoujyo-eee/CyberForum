package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.AdministratorInfo;
import org.example.cyberforum.entities.Administrator;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@RestController
public class  AdministratorController
{

    @Autowired
    private AdministratorService administratorService;

    // region functions for ...

    @GetMapping("/get_administrator/{forum_id}")
    public ServiceResponse<List<User>> getAdministrator(@PathVariable("forum_id") Long forumId)
    {
        return administratorService.getAdministratorByForumId(forumId);
    }

    @PostMapping("/add_administrator")
    public ServiceResponse<String> addAdministrator(@RequestBody AdministratorInfo administrator)
    {
        return administratorService.addAdministrator(administrator.getForumId(), administrator.getUserName());
    }

    // endregion functions for ...

    @DeleteMapping("/delete_administrator/{forum_id}")
    public ServiceResponse<Boolean> deleteAdministrator(@RequestBody AdministratorInfo administrator, @CookieValue("user_id") Long adminId)
    {
        return administratorService.deleteAdministrator(administrator.getForumId(), administrator.getUserId(), adminId);
    }

    @RequestMapping("/is_administrator/{blog_id}")
    public ServiceResponse<Boolean> ifAdministratorFromBlog(@PathVariable("blog_id") Long blogId, @RequestBody User user)
    {
        ServiceResponse<Boolean> result = administratorService.ifAdministratorFromBlog(blogId, user.getId());
        log.info("result = {}", result);
        return result;
    }

    @PostMapping("/is_administrator_by_forum_id/{forum_id}")
    public ServiceResponse<Boolean> ifAdministratorFromForum(@RequestBody AdministratorInfo administrator)
    {
        log.info("administrator = {}", administrator);
        ServiceResponse<Boolean> result = administratorService.ifAdministratorFromForum(administrator.getUserId(), administrator.getForumId());
        log.info("result = {}", result);
        return result;
    }
}
