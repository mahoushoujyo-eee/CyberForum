package org.example.cyberforum.service;

import org.example.cyberforum.bean.User;
import org.example.cyberforum.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService
{

    @Autowired
    AdministratorMapper administratorMapper;

    @Autowired
    UserService userService;

    public List<User> getAdministratorByForumId(Long forumId)
    {
        List<User> administrators = administratorMapper.getAdministratorByForumId(forumId);
        return administrators;
    }

    public void addAdministrator(Long forumId, String userName)
    {
        administratorMapper.addAdministrator(forumId, userService.getUserIdByUserName(userName));
    }

    public void deleteAdministrator(Long forumId, String userName)
    {
        administratorMapper.deleteAdministrator(forumId, userService.getUserIdByUserName(userName));
    }
}
