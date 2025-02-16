package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.PaginatedData;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class BlogService
{
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ForumService forumService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private UserService userService;

    // PageSize不能在后端写死，必须是前端传过来的
    private static final int PAGE_SIZE = 10;

    @Transactional(rollbackFor = Exception.class)
    public void putOutNewBlog(Blog blog)
    {
        // DEFAULT NOW()
        blogMapper.addBlog(blog);
    }

    public ServiceResponse<List<BlogInfo>> getLatestBlogList()
    {
        return ServiceResponse.buildSuccessResponse(blogMapper.getLatestBlogInfoList());
    }

    public ServiceResponse<BlogInfo> getBlogById(Long id)
    {
        if (!containsBlogOfId(id))
            return ServiceResponse.buildErrorResponse(-100,"blog not found");

        return ServiceResponse.buildSuccessResponse(blogMapper.getBlogInfoById(id));
    }


    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteBlogById(Long id)
    {
        if (!containsBlogOfId(id))
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
        if (!forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        List<BlogInfo> blogs = blogMapper.getBlogInfoByForumIdWithTop(forumId);

        return ServiceResponse.buildSuccessResponse(blogs);
    }

    public ServiceResponse<PaginatedData<BlogInfo>> getBlogsByForumIdWithTop(Long forumId, int pageIndex, int pageSize)
    {
        if (!forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        if (pageIndex < 1)
            return ServiceResponse.buildErrorResponse(-100, "page index must be positive");

        PaginatedData<BlogInfo> paginatedData = new PaginatedData<>();
        List<BlogInfo> blogs = blogMapper.getPaginatedBlogInfoByForumIdWithTop(forumId, (pageIndex-1)*pageSize, pageSize);
        Long blogsCount = blogMapper.getBlogsCount(forumId);

        paginatedData.setData(blogs);
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(pageSize);
        paginatedData.setPageCount((int) Math.ceil(blogsCount / (float)pageSize));
        paginatedData.setTotal(blogsCount);

        if (pageIndex > paginatedData.getPageCount())
            return ServiceResponse.buildErrorResponse(-100, "page index out of range");

        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> cancelTop(Long blogId, Long userId)
    {
        Long forumId = getForumIdByBlogId(blogId);
        if (!administratorService.ifAdministrator(forumId, userId).getData())
            return ServiceResponse.buildErrorResponse(-100, "you are not an administrator");
        blogMapper.cancelTop(blogId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> putTop(Long blogId)
    {
        if (!containsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        blogMapper.putTop(blogId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    // 分页
    public ServiceResponse<List<BlogInfo>> searchBlog(String keyword)
    {
        List<BlogInfo> blogs = blogMapper.getBlogInfoList().stream().filter(blog -> blog.getTitle().contains(keyword)).toList();

        return ServiceResponse.buildSuccessResponse(blogs);
    }

    public ServiceResponse<PaginatedData<BlogInfo>> searchBlog(String keyword, int pageIndex)
    {
        if (pageIndex < 1)
            return ServiceResponse.buildErrorResponse(-100, "page index must be positive");

        PaginatedData<BlogInfo> paginatedData = new PaginatedData<>();
        List<BlogInfo> blogs = blogMapper.getBlogInfoList();
        blogs = blogs.stream().filter(blog -> blog.getTitle().contains(keyword)).toList();
        paginatedData.setData(blogs);
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(PAGE_SIZE);
        paginatedData.setPageCount((int) Math.ceil(blogs.size() / (float)PAGE_SIZE));
        paginatedData.setTotal(blogs.size());

        if (pageIndex > paginatedData.getPageCount())
            return ServiceResponse.buildErrorResponse(-100, "page index out of range");

        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    public ServiceResponse<PaginatedData<BlogInfo>> searchBlogOfForum(String searchText, Long forumId, int pageIndex)
    {
        if (!forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum not found");

        if (pageIndex < 1)
            return ServiceResponse.buildErrorResponse(-100, "page index must be positive");

        PaginatedData<BlogInfo> paginatedData = new PaginatedData<>();
        List<BlogInfo> blogs = blogMapper.getBlogInfoByForumId(forumId);
        blogs = blogs.stream().filter(blog -> blog.getTitle().contains(searchText)).toList();
        paginatedData.setData(blogs);
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(PAGE_SIZE);
        paginatedData.setPageCount((int) Math.ceil(blogs.size() / (float)PAGE_SIZE));
        paginatedData.setTotal(blogs.size());

        if (pageIndex > paginatedData.getPageCount())
            return ServiceResponse.buildErrorResponse(-100, "page index out of range");

        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    public boolean containsUser(Long userId)
    {
        return userService.containsUser(userId);
    }

    // 如果只是判断是否存在，应该用count
    public boolean containsBlogOfId(Long blogId)
    {
        return blogMapper.containsBlogOfId(blogId);
    }
}
