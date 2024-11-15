package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.bean.Forum;
import org.example.cyberforum.mapper.BlogMapper;
import org.example.cyberforum.mapper.ForumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ForumService
{

    @Autowired
    ForumMapper forumMapper;
    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

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
        List<Blog> blogs = blogMapper.getBlogsByForumId(id);
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(getForumById(blog.getForumId()).getName());
        }

        blogs.sort((blog1, blog2) -> blog2.isTop() ? 1 : -1);
        log.info("get blogs by forum id: " + id + " blogs: " + blogs);

        return blogs;
    }

    public List<Forum> searchForum(String searchText)
    {
        return forumMapper.getForumList().stream().filter(forum -> forum.getName().contains(searchText)).toList();
    }
}
