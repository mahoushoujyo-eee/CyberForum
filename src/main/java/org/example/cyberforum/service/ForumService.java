package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.mapper.BlogMapper;
import org.example.cyberforum.mapper.ForumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ForumService
{

    // 论坛的创建

    @Autowired
    ForumMapper forumMapper;
    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    // 分页
    public List<Forum> getForumList()
    {
        return forumMapper.getForumList();
    }

    public Forum getForumById(Long id)
    {
        return forumMapper.getForumById(id);
    }

    // 分页
    public List<Blog> getBlogList(Long id)
    {
        List<Blog> blogs = blogMapper.getBlogsByForumId(id);
        for (Blog blog: blogs)
        {
            blog.setUsername(userService.getUserById(blog.getUserId()).getUserName());
            blog.setForumName(getForumById(blog.getForumId()).getName());
        }

        blogs.sort((blog1, blog2) -> blog2.isTop() ? 1 : -1);
        log.info("get blogs by forum id: " + id + " blogs: " + blogs);

        return blogs;
    }

    public List<Forum> searchForum(String searchText)
    {
        return forumMapper.getForumList().stream().filter(forum -> forum.getName().contains(searchText)).toList();
    }

    public boolean ifContainsForum(Long forumId)
    {
        return forumMapper.getForumById(forumId) != null;
    }
}
