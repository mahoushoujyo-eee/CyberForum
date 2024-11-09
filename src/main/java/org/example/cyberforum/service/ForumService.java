package org.example.cyberforum.service;

import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.bean.Forum;
import org.example.cyberforum.mapper.BlogMapper;
import org.example.cyberforum.mapper.ForumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService
{

    @Autowired
    ForumMapper forumMapper;
    @Autowired
    BlogMapper blogMapper;

    public List<Forum> getForumList()
    {
        return forumMapper.getForumList();
    }

    public Forum getForumById(Long id)
    {
        return forumMapper.getForumById(id);
    }

    public List<Blog> getBlogList(Long id)
    {
        return blogMapper.getBlogByForumId(id);
    }
}
