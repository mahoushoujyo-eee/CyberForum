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

    public void putOutNewBlog(Blog blog)
    {
        blog.setCreateTime(new Date());
        logger.info("BlogService putOutNewBlog: put out new blog: " + blog);
        blogMapper.addBlog(blog);
    }

    public List<Blog> getLatestBlogList()
    {
        return blogMapper.getLatestBlogList();
    }

    public Blog getBlogById(Long id)
    {
        return blogMapper.getBlogById(id);
    }
}
