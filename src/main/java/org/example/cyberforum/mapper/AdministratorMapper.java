package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.entities.Administrator;
import org.example.cyberforum.entities.User;

import java.util.List;

@Mapper
public interface AdministratorMapper
{
    List<User> getAdministratorByForumId(Long forumId);

    void addAdministrator(Long forumId, Long userId);

    void deleteAdministrator(Long forumId, Long userId);

    List<Administrator> getAdministrators();

    boolean containsAdministrator(Long forumId, Long userId);
}
