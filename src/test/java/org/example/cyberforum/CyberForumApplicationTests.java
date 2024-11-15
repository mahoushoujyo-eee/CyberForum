package org.example.cyberforum;

import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.mapper.*;
import org.example.cyberforum.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest
class CyberForumApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ForumMapper forumMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    private BlogService blogService;

    @Test
    void contextLoads()
    {
        System.out.println(forumMapper.getForumList());
    }

    @Test
    void test()
    {
        System.out.println(TimeZone.getDefault());
        System.out.println(new java.util.Date());
    }

    @Test
    void test01()
    {
        Blog blog = new Blog();
        blog.setUserId(1L);
        blog.setForumId(1L);
        blog.setTitle("test");
        blog.setContent("test");
        blog.setCreateTime(new Date());
        System.out.println(blog);
        blogMapper.addBlog(blog);
    }

    @Test
    void test02()
    {
        System.out.println(blogMapper.getLatestBlogList());
    }

    @Test
    void test03()
    {
        System.out.println(administratorMapper.getAdministratorByForumId(1L));
    }

    @Test
    void test04()
    {
        System.out.println(commentMapper.getCommentsByBlogId(1L));
    }

    @Test
    void test05()
    {
        List<Blog> blogList = blogService.getBlogsByForumIdWithTop(2L);
        for (Blog blog: blogList)
        {
            System.out.println(blog.getId());
        }
    }

}
