package org.example.cyberforum.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.bean.Blog;
import org.example.cyberforum.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class BlogController
{

    @Autowired
    BlogService blogService;

    @RequestMapping("/put_blog")
    @ResponseBody
    public boolean putOutNewBlog(@RequestBody Blog blog)
    {
        blogService.putOutNewBlog(blog);
        return true;
    }

    @RequestMapping("/get_latest_blogs")
    @ResponseBody
    public List<Blog> getLatestBlog()
    {
        return blogService.getLatestBlogList();
    }

    @RequestMapping("/blog/{id}")
    @ResponseBody
    public Blog getBlogById(@PathVariable Long id)
    {
        log.info("get blog by id: " + id);
        log.info("blog: " + blogService.getBlogById(id));
        return blogService.getBlogById(id);
    }

    @DeleteMapping("/delete_blog/{blog_id}")
    @ResponseBody
    public boolean deleteBlog(@PathVariable("blog_id") Long blogId)
    {
        blogService.deleteBlogById(blogId);
        return true;
    }

    @PutMapping("/delete_top/{blog_id}")
    @ResponseBody
    public boolean deleteTop(@PathVariable("blog_id") Long blogId)
    {
        blogService.deleteTop(blogId);
        return true;
    }

    @PutMapping("/put_top/{blog_id}")
    @ResponseBody
    public boolean putTop(@PathVariable("blog_id") Long blogId)
    {
        blogService.putTop(blogId);
        return true;
    }

    @GetMapping("/search_blog/{searchText}")
    @ResponseBody
    public List<Blog> searchBlog(@PathVariable("searchText") String searchText)
    {
        return blogService.searchBlog(searchText);
    }

}
