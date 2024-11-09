package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.bean.Forum;

import java.util.List;

@Mapper
public interface ForumMapper
{
    void addForum(Forum forum);

    List<Forum> getForumList();

    Forum getForumById(Long id);
}
