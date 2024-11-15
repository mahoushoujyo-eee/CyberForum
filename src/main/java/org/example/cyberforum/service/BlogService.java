package org.example.cyberforum.service;

import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
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

    public void deleteBlogById(Long id)
    {
        blogMapper.deleteBlogById(id);
    }

    public Long getForumIdByBlogId(Long blogId)
    {
        return blogMapper.getBlogById(blogId).getForumId();
    }

    public List<Blog> getBlogsByForumIdWithTop(Long forumId)
    {
        List<Blog> blogs = blogMapper.getBlogsByForumId(forumId);
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }

        blogs.sort((blog1, blog2) -> blog2.isTop() ? 1 : -1);
        logger.info("get blogs by forum id: " + forumId + " blogs: " + blogs);

        return blogs;
    }

    public boolean deleteTop(Long blogId)
    {
        blogMapper.deleteTop(blogId);
        return true;
    }

    public boolean putTop(Long blogId)
    {
        blogMapper.putTop(blogId);
        return true;
    }

    public List<Blog> searchBlog(String searchText)
    {
        List<Blog> blogs = blogMapper.getBlogList().stream().filter(blog -> blog.getTitle().contains(searchText)).toList();
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }
        logger.info("search blog: " + searchText + " blogs: " + blogs);
        return blogs;
    }

    public List<Blog> searchBlogOfForum(String searchText, Long forumId)
    {
        List<Blog> blogs = blogMapper.getBlogsByForumId(forumId).stream().filter(blog -> blog.getTitle().contains(searchText)).toList();
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }

        logger.info("search blog of forum: " + searchText + " blogs: " + blogs);
        return blogs;
    }
}
