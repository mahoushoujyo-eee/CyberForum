package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.service.AdministratorService;
import org.example.cyberforum.service.ForumService;
import org.example.cyberforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@RestController
public class ForumController
{
    @Autowired
    private ForumService forumService;
    @Autowired
    private UserService userService;

    @GetMapping("/initialize_forum")
    public ServiceResponse<List<Forum>> getForumList()
    {
        return forumService.getForumList();
    }


    @GetMapping("/forum/{id}")
    public ServiceResponse<Forum> getForumById(@PathVariable Long id)
    {
        return forumService.getForumById(id);
    }

    @GetMapping("/forum/{id}/blog")
    public ServiceResponse<List<BlogInfo>> getBlogList(@PathVariable("id") Long forumId)
    {
        return forumService.getBlogList(forumId);
    }

    @GetMapping("search/forum/{searchText}")
    public ServiceResponse<List<Forum>> searchForum(@PathVariable("searchText") String searchText)
    {
        return forumService.searchForum(searchText);
    }
}
