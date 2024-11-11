package org.example.cyberforum.service;

import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogService
{

    private static final Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    @Autowired
    ForumService forumService;

    public void putOutNewBlog(Blog blog)
    {
        blog.setCreateTime(new Date());
        logger.info("BlogService putOutNewBlog: put out new blog: " + blog);
        blogMapper.addBlog(blog);
    }

    public List<Blog> getLatestBlogList()
    {
        List<Blog> blogs = blogMapper.getLatestBlogList();
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }

        return blogs;
    }

    public Blog getBlogById(Long id)
    {
        Blog blog =  blogMapper.getBlogById(id);
        blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
        blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        logger.info("get blog by id: " + id + " blog: " + blog);
        return blog;
    }
}
