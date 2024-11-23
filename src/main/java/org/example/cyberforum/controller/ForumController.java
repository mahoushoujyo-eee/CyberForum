package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.service.AdministratorService;
import org.example.cyberforum.service.ForumService;
import org.example.cyberforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ForumController
{
    @Autowired
    ForumService forumService;
    @Autowired
    UserService userService;
    @Autowired
    AdministratorService administratorService;

    @GetMapping("/initialize_forum")
    public List<Forum> getForumList()
    {
        List<Forum> forums = forumService.getForumList();
        log.info("forum list: " + forums);

        return forums;
    }


    @GetMapping("/forum/{id}")
    public Forum getForumById(@PathVariable Long id)
    {
        Forum forum = forumService.getForumById(id);
        log.info("forum: " + forum);

        return forum;
    }

    @GetMapping("/forum/{id}/blog")
    public List<Blog> getBlogList(@PathVariable("id") Long forumId)
    {
        List<Blog> blogs = forumService.getBlogList(forumId);
        log.info("ForumController getBlogListByForumId: blog list: " + blogs);
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
        log.info("searchForum: " + forums);

        return forums;
    }
}
