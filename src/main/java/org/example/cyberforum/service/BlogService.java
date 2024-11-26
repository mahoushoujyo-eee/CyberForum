package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class BlogService
{

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ForumService forumService;
    @Autowired
    private AdministratorService administratorService;

    @Transactional(rollbackFor = Exception.class)
    public void putOutNewBlog(Blog blog)
    {
        // creation time.
        blog.setCreateTime(new Date());
        log.info("BlogService putOutNewBlog: put out new blog: {}", blog);
        blogMapper.addBlog(blog);
    }

    public ServiceResponse<List<BlogInfo>> getLatestBlogList()
    {
        return ServiceResponse.buildSuccessResponse(blogMapper.getLatestBlogInfoList());
    }

    public ServiceResponse<BlogInfo> getBlogById(Long id)
    {
        if (!ifContainsBlogOfId(id))
            return ServiceResponse.buildErrorResponse(-100,"blog not found");
        // SELECT FROM blog LEFT JOIN user ON ... LEFT JOIN forum ON ...

        return ServiceResponse.buildSuccessResponse(blogMapper.getBlogInfoById(id));
    }


    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteBlogById(Long id)
    {
        if (!ifContainsBlogOfId(id))
            return ServiceResponse.buildErrorResponse(-100,"blog not found");

        blogMapper.deleteBlogById(id);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public Long getForumIdByBlogId(Long blogId)
    {
        return blogMapper.getBlogById(blogId).getForumId();
    }

    public ServiceResponse<List<BlogInfo>> getBlogsByForumIdWithTop(Long forumId)
    {
        if (!forumService.ifContainsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        List<BlogInfo> blogs = blogMapper.getBlogInfoByForumId(forumId);

        blogs.sort((blog1, blog2) -> blog2.isTop() ? 1 : -1);
        log.info("get blogs by forum id: {} blogs: {}", forumId, blogs);

        return ServiceResponse.buildSuccessResponse(blogs);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> cancelTop(Long blogId, Long userId)
    {
        if (!administratorService.ifAdministratorFromBlog(blogId, userId).getData())
            return ServiceResponse.buildErrorResponse(-100, "you are not an administrator");
        blogMapper.cancelTop(blogId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> putTop(Long blogId)
    {
        if (!ifContainsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        blogMapper.putTop(blogId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    // 分页
    public List<BlogInfo> searchBlog(String keyword)
    {
        List<BlogInfo> blogs = blogMapper.getBlogInfoList().stream().filter(blog -> blog.getTitle().contains(keyword)).toList();

        log.info("search blog: {} blogs: {}", keyword, blogs);
        return blogs;
    }

    // 分页
    public List<BlogInfo> searchBlogOfForum(String searchText, Long forumId)
    {
        if (!forumService.ifContainsForum(forumId))
            return null;

        List<BlogInfo> blogs = blogMapper.getBlogInfoByForumId(forumId).stream().filter(blog -> blog.getTitle().contains(searchText)).toList();

        log.info("search blog of forum: {} blogs: {}", searchText, blogs);
        return blogs;
    }


    public boolean ifContainsBlog(Blog blog)
    {
        return blogMapper.ifContainsBlog(blog);
    }

    public boolean ifContainsBlog(BlogInfo blogInfo)
    {
        Blog blog = new Blog();
        blog.setTitle(blogInfo.getTitle());
        blog.setContent(blogInfo.getContent());
        blog.setUserId(blogInfo.getUserId());
        blog.setForumId(blogInfo.getForumId());
        return blogMapper.ifContainsBlog(blog);
    }

    public boolean ifContainsBlogOfId(Long blogId)
    {
        return blogMapper.getBlogById(blogId) != null;
    }
}
