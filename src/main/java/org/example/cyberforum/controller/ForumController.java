package org.example.cyberforum.controller;

import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.bean.Forum;
import org.example.cyberforum.service.ForumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumController
{
    @Autowired
    ForumService forumService;

    private static final Logger logger = LoggerFactory.getLogger(ForumController.class);

    @RequestMapping("/initialize_forum")
    public List<Forum> getForumList()
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
        return blogs;
    }
}