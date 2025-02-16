package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.entities.Forum;

import java.util.List;

@Mapper
public interface ForumMapper
{
    void addForum(Forum forum);

    List<Forum> getForumList();
    List<Forum> getPaginatedForumList(int startIndex, int pageSize);

    Forum getForumById(Long id);

    boolean containsForum(Long id);

    boolean containsForumByName(String forumName);

    Long getForumCount();
}
