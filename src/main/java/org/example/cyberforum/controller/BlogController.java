package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@RestController
public class BlogController
{

    @Autowired
    private BlogService blogService;

    @PostMapping("/put_blog")
    public void putOutNewBlog(@RequestBody Blog blog)
    {
        blogService.putOutNewBlog(blog);
    }

    @GetMapping("/get_latest_blogs")
    public ServiceResponse<List<BlogInfo>> getLatestBlog()
    {
        return blogService.getLatestBlogList();
    }

    @GetMapping("/blog/{id}")
    public ServiceResponse<BlogInfo> getBlogById(@PathVariable Long id)
    {
        return blogService.getBlogById(id);
    }

    @DeleteMapping("/delete_blog/{blog_id}")
    public ServiceResponse<Boolean> deleteBlogById(@PathVariable("blog_id") Long blogId)
    {
        return blogService.deleteBlogById(blogId);
    }

    @PutMapping("/delete_top/{blog_id}")
    public ServiceResponse<Boolean> deleteTop(@PathVariable("blog_id") Long blogId, @CookieValue("user_id") Long userId)
    {
        return blogService.cancelTop(blogId, userId);
    }

    @PutMapping("/put_top/{blog_id}")
    public ServiceResponse<Boolean> putTop(@PathVariable("blog_id") Long blogId)
    {
        return blogService.putTop(blogId);
    }

    @GetMapping("/search_blog/{search_text}")
    public List<BlogInfo> searchBlog(@PathVariable("search_text") String searchText)
    {
        return blogService.searchBlog(searchText);
    }

    @GetMapping("/search_blog_of_forum")
    public List<BlogInfo> searchBlogOfForum(@RequestParam("searchText") String searchText, @RequestParam("forumId") Long forumId)
    {
        return blogService.searchBlogOfForum(searchText, forumId);
    }

}
