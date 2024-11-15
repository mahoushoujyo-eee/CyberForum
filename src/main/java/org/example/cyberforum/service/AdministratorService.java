package org.example.cyberforum.service;

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
    public void addAdministrator(Long forumId, String userName)
    {
        administratorMapper.addAdministrator(forumId, userService.getUserIdByUserName(userName));
    }

    public void deleteAdministrator(Long forumId, String userName)
    {
        administratorMapper.deleteAdministrator(forumId, userService.getUserIdByUserName(userName));
    }

    public boolean ifAdministrator(Long blogId, Long userId)
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

    public boolean isAdministrator(Long userId, Long forumId)
    {
        List<User> administrators = administratorMapper.getAdministratorByForumId(forumId);
        for (User administrator: administrators)
        {
            if (administrator.getId().equals(userId))
                return true;
        }
        return false;
    }
}
