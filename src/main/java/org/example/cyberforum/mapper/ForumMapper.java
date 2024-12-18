package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.entities.Forum;

import java.util.List;

@Mapper
public interface ForumMapper
{
    void addForum(Forum forum);

    List<Forum> getForumList();

    Forum getForumById(Long id);

    boolean ifContainsForum(Long id);

    boolean ifContainsForumByName(String forumName);
}
