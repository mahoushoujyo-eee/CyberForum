package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BlogService
{

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
        log.info("BlogService putOutNewBlog: put out new blog: " + blog);
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

        if (blog == null)
        {
            return null;
        }

        blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
        blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        log.info("get blog by id: " + id + " blog: " + blog);
        return blog;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBlogById(Long id)
    {
        if (!ifContainsBlogOfId(id))
        {
            return false;
        }

        blogMapper.deleteBlogById(id);
        return true;
    }

    public Long getForumIdByBlogId(Long blogId)
    {
        return blogMapper.getBlogById(blogId).getForumId();
    }

    public List<Blog> getBlogsByForumIdWithTop(Long forumId)
    {
        if (!forumService.ifContainsForum(forumId))
            return null;

        List<Blog> blogs = blogMapper.getBlogsByForumId(forumId);
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }

        blogs.sort((blog1, blog2) -> blog2.isTop() ? 1 : -1);
        log.info("get blogs by forum id: " + forumId + " blogs: " + blogs);

        return blogs;
    }


    @Transactional(rollbackFor = Exception.class)
    public void cancelTop(Long blogId)
    {

        blogMapper.cancelTop(blogId);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean putTop(Long blogId)
    {
        if (!ifContainsBlogOfId(blogId))
            return false;

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
        log.info("search blog: " + searchText + " blogs: " + blogs);
        return blogs;
    }

    public List<Blog> searchBlogOfForum(String searchText, Long forumId)
    {
        if (!forumService.ifContainsForum(forumId))
            return null;

        List<Blog> blogs = blogMapper.getBlogsByForumId(forumId).stream().filter(blog -> blog.getTitle().contains(searchText)).toList();
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }

        log.info("search blog of forum: " + searchText + " blogs: " + blogs);
        return blogs;
    }


    public boolean ifContainsBlogOfInfo(Blog blog)
    {
        List<Blog> blogs = blogMapper.getBlogList();

        for (Blog blog1: blogs)
        {
            if (blog1.getTitle().equals(blog.getTitle())
                    && blog1.getContent().equals(blog.getContent()))
                return true;
        }
        return false;
    }

    public boolean ifContainsBlogOfId(Long blogId)
    {
        return blogMapper.getBlogById(blogId) != null;
    }
}
