package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.PaginatedData;
import stark.dataworks.boot.web.ServiceResponse;
import java.util.List;

@Slf4j
@RestController
public class ForumController
{
    @Autowired
    private ForumService forumService;

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

    @GetMapping("/forum/{id}/blog/{pageIndex}")
    public ServiceResponse<PaginatedData<BlogInfo>> getBlogList(@PathVariable("id") Long forumId, @PathVariable("pageIndex") int pageIndex)
    {
        return forumService.getBlogList(forumId, pageIndex);
    }

    @GetMapping("search_forum")
    public ServiceResponse<PaginatedData<Forum>> searchForum(@RequestParam("searchText") String searchText, @RequestParam("pageIndex") int pageIndex)
    {
        return forumService.searchForum(searchText, pageIndex);
    }

    @PostMapping("/createForum")
    public ServiceResponse<Boolean> createForum(@RequestBody Forum forum)
    {
        return forumService.crateForum(forum);
    }
}
