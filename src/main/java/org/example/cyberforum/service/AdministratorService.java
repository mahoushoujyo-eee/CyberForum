package org.example.cyberforum.service;

import org.example.cyberforum.bean.Administrator;
import org.example.cyberforum.bean.User;
import org.example.cyberforum.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministratorService
{

    @Autowired
    AdministratorMapper administratorMapper;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    public List<User> getAdministratorByForumId(Long forumId)
    {
        List<User> administrators = administratorMapper.getAdministratorByForumId(forumId);
        return administrators;
    }

    @Transactional(rollbackFor = Exception.class)
    public String addAdministrator(Long forumId, String username)
    {
        Long userId = userService.getUserIdByUserName(username);

        if (userId == null)
        {
            return "null";
        }

        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        administrator.setForumId(forumId);

        if (ifContainsAdministrator(administrator))
        {
            return "false";
        }


        administratorMapper.addAdministrator(forumId, userId);
        return "true";
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAdministrator(Long forumId, Long userId)
    {
        administratorMapper.deleteAdministrator(forumId, userId);
    }

    public boolean ifAdministratorFromBlog(Long blogId, Long userId)
    {
        Long forumId = blogService.getForumIdByBlogId(blogId);
        List<User> administrators = administratorMapper.getAdministratorByForumId(forumId);
        for (User administrator: administrators)
        {
            if (administrator.getId().equals(userId))
                return true;
        }
        return false;
    }

    public boolean ifAdministratorFromForum(Long userId, Long forumId)
    {
        List<User> administrators = administratorMapper.getAdministratorByForumId(forumId);
        for (User administrator: administrators)
        {
            if (administrator.getId().equals(userId))
                return true;
        }
        return false;
    }

    public boolean ifContainsAdministrator(Administrator administrator)
    {
        List<Administrator> administrators = administratorMapper.getAdministrators();
        for (Administrator administrator1: administrators)
        {
            if (administrator1.getUserId().equals(administrator.getUserId())
                    && administrator1.getForumId().equals(administrator.getForumId()))
                return true;
        }
        return false;
    }
}
