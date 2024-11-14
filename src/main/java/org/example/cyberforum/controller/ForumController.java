package org.example.cyberforum.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.bean.Forum;
import org.example.cyberforum.service.AdministratorService;
import org.example.cyberforum.service.ForumService;
import org.example.cyberforum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumController
{
    @Autowired
    ForumService forumService;
    @Autowired
    UserService userService;
    @Autowired
    AdministratorService administratorService;


    private static final Logger logger = LoggerFactory.getLogger(ForumController.class);
    @Autowired
    private JdbcRepositoriesAutoConfiguration jdbcRepositoriesAutoConfiguration;

    @RequestMapping("/initialize_forum")
    public List<Forum> getForumList(HttpServletRequest request, HttpServletResponse response)
    {
        List<Forum> forums = forumService.getForumList();
        logger.info("forum list: " + forums);

        return forums;
    }


    @RequestMapping("/forum/{id}")
    public Forum getForumById(@PathVariable Long id)
    {
        Forum forum = forumService.getForumById(id);
        logger.info("forum: " + forum);

        return forum;
    }

    @RequestMapping("/forum/{id}/blog")
    public List<Blog> getBlogList(@PathVariable Long id)
    {
        List<Blog> blogs = forumService.getBlogList(id);
        logger.info("ForumController getBlogListByForumId: blog list: " + blogs);
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(forumService.getForumById(blog.getForumId()).getName());
        }
        return blogs;
    }

    @GetMapping("search/forum/{searchText}")
    public List<Forum> searchForum(@PathVariable("searchText") String searchText)
    {
        List<Forum> forums = forumService.searchForum(searchText);
        logger.info("searchForum: " + forums);

        return forums;
    }
}
