package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.mapper.ForumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.PaginatedData;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class ForumService
{
    private static final int PAGE_SIZE = 10;
    @Autowired
    private ForumMapper forumMapper;
    @Lazy
    @Autowired
    private BlogService blogService;
    @Lazy
    @Autowired
    private AdministratorService administratorService;

    // 分页
    public ServiceResponse<PaginatedData<Forum>> getForumList(int pageIndex)
    {
        PaginatedData<Forum> paginatedData = new PaginatedData<>();
        paginatedData.setData(forumMapper.getPaginatedForumList((pageIndex-1)*PAGE_SIZE, PAGE_SIZE));
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(PAGE_SIZE);
        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    public ServiceResponse<Forum> getForumById(Long id)
    {
        if (!containsForum(id))
            return ServiceResponse.buildErrorResponse(-100, "forum not found");
        return ServiceResponse.buildSuccessResponse(forumMapper.getForumById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> crateForum(Forum forum)
    {
        ServiceResponse<Boolean> addForumResponse = addForum(forum);
        if (!addForumResponse.isSuccess())
            return ServiceResponse.buildErrorResponse(-100, addForumResponse.getMessage());

        if (!administratorService.addAdministrator(forum.getId(), forum.getOwnerId()).isSuccess())
            return ServiceResponse.buildErrorResponse(-100, "添加创建者为管理员时出现故障");
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addForum(Forum forum)
    {
        if (containsForumByName(forum.getName()))
            return ServiceResponse.buildErrorResponse(-100, "该论坛名已存在");
        forumMapper.addForum(forum);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<PaginatedData<BlogInfo>> getBlogList(Long forumId, int pageIndex, int pageSize)
    {
        if (!containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum not found");

        return blogService.getBlogsByForumIdWithTop(forumId, pageIndex, pageSize);
    }

    public ServiceResponse<PaginatedData<Forum>> searchForum(String searchText, int pageIndex)
    {
        // 分页的set方法，如果要用到，可以写个build方法，如果不需要，可以简化
        PaginatedData<Forum> paginatedData = new PaginatedData<>();
        paginatedData.setData(forumMapper.getForumList().stream().filter(forum -> forum.getName().contains(searchText)).toList());
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(PAGE_SIZE);
        paginatedData.setPageCount((int) Math.ceil(paginatedData.getData().size() / (float)PAGE_SIZE));
        paginatedData.setTotal(paginatedData.getData().size());
        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    public boolean containsForum(Long forumId)
    {
        return forumMapper.containsForum(forumId);
    }

    public boolean containsForumByName(String forumName)
    {
        return forumMapper.containsForumByName(forumName);
    }
}
