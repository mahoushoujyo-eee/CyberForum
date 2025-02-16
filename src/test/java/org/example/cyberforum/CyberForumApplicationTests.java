package org.example.cyberforum;

import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.mapper.*;
import org.example.cyberforum.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.TimeZone;

@SpringBootTest
class CyberForumApplicationTests
{
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
        blog.setCreationTime(new Date());
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
    void testUserMapper()
    {
        System.out.println(userMapper.containsUserByUserName("eee"));
    }

    @Test
    void testCommentMapper()
    {
        System.out.println(commentMapper.getCommentInfoListByBlogId(34L));
    }

    @Test
    void testLogBlogOfForm()
    {
        System.out.println(blogService.getBlogsByForumIdWithTop(1L));
    }

    @Test
    void testKeyProperties()
    {
        Forum forum = new Forum();
        forum.setName("test");
        forum.setOwnerId(1L);
        forumMapper.addForum(forum);
        System.out.println(forum.getId());
    }

}
