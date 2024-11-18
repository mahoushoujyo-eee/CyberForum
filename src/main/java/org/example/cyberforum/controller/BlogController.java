package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BlogController
{

    @Autowired
    BlogService blogService;

    @PostMapping("/put_blog")
    public boolean putOutNewBlog(@RequestBody Blog blog)
    {
        blogService.putOutNewBlog(blog);
        return true;
    }

    @GetMapping("/get_latest_blogs")
    public List<Blog> getLatestBlog()
    {
        return blogService.getLatestBlogList();
    }

    @GetMapping("/blog/{id}")
    public Blog getBlogById(@PathVariable Long id)
    {
        Blog blog = blogService.getBlogById(id);
        log.info("get blog by id: " + id);
        log.info("blog: " + blog);

        return blogService.getBlogById(id);
    }

    @DeleteMapping("/delete_blog/{blog_id}")
    public boolean deleteBlogById(@PathVariable("blog_id") Long blogId)
    {
        return blogService.deleteBlogById(blogId);
    }

    @PutMapping("/delete_top/{blog_id}")
    public boolean deleteTop(@PathVariable("blog_id") Long blogId)
    {
        blogService.cancelTop(blogId);
        return true;
    }

    @PutMapping("/put_top/{blog_id}")
    public boolean putTop(@PathVariable("blog_id") Long blogId)
    {
        return blogService.putTop(blogId);
    }

    @GetMapping("/search_blog/{search_text}")
    public List<Blog> searchBlog(@PathVariable("search_text") String searchText)
    {
        return blogService.searchBlog(searchText);
    }

    @GetMapping("/search_blog_of_forum")
    public List<Blog> searchBlogOfForum(@RequestParam("searchText") String searchText, @RequestParam("forumId") Long forumId)
    {
        return blogService.searchBlogOfForum(searchText, forumId);
    }

}
