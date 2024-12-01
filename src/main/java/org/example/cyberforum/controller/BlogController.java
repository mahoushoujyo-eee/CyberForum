package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.service.BlogService;
import org.example.cyberforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.PaginatedData;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@RestController
public class BlogController
{
    @Autowired
    private BlogService blogService;
    @Lazy
    @Autowired
    private UserService userService;

    @PostMapping("/put_blog")
    public ServiceResponse<Boolean> putOutNewBlog(@RequestBody Blog blog, @CookieValue("userId") Long userId)
    {
        if (!userService.ifContainsUser(userId))
            return ServiceResponse.buildErrorResponse(-100, "用户不存在");

        blogService.putOutNewBlog(blog);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @GetMapping("/get_latest_blogs")
    public ServiceResponse<List<BlogInfo>> getLatestBlog()
    {
        return blogService.getLatestBlogList();
    }

    @GetMapping("/blog/{id}")
    public ServiceResponse<BlogInfo> getBlogById(@PathVariable("id") Long id)
    {
        return blogService.getBlogById(id);
    }

    @DeleteMapping("/delete_blog/{blog_id}")
    public ServiceResponse<Boolean> deleteBlogById(@PathVariable("blog_id") Long blogId)
    {
        return blogService.deleteBlogById(blogId);
    }

    @PutMapping("/delete_top/{blog_id}")
    public ServiceResponse<Boolean> deleteTop(@PathVariable("blog_id") Long blogId, @CookieValue("userId") Long userId)
    {
        return blogService.cancelTop(blogId, userId);
    }

    @PutMapping("/put_top/{blog_id}")
    public ServiceResponse<Boolean> putTop(@PathVariable("blog_id") Long blogId)
    {
        return blogService.putTop(blogId);
    }

    @GetMapping("/search_blog/{search_text}")
    public ServiceResponse<List<BlogInfo>> searchBlog(@PathVariable("search_text") String searchText)
    {
        return blogService.searchBlog(searchText);
    }

    @GetMapping("/search_blog")
    public ServiceResponse<PaginatedData<BlogInfo>> searchBlog(@RequestParam("searchText") String searchText, @RequestParam("pageIndex") int pageIndex)
    {
        return blogService.searchBlog(searchText, pageIndex);
    }

    @GetMapping("/search_blog_of_forum")
    public ServiceResponse<PaginatedData<BlogInfo>> searchBlogOfForum(@RequestParam("searchText") String searchText, @RequestParam("forumId") Long forumId, @RequestParam("pageIndex") int pageIndex)
    {
        return blogService.searchBlogOfForum(searchText, forumId, pageIndex);
    }
}
