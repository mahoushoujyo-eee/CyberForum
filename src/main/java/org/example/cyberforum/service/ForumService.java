package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;
import org.example.cyberforum.entities.Forum;
import org.example.cyberforum.mapper.BlogMapper;
import org.example.cyberforum.mapper.ForumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class ForumService
{

    // 论坛的创建

    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    // 分页
    public ServiceResponse<List<Forum>> getForumList()
    {
        return ServiceResponse.buildSuccessResponse(forumMapper.getForumList());
    }

    public ServiceResponse<Forum> getForumById(Long id)
    {
        if (!ifContainsForum(id))
            return ServiceResponse.buildErrorResponse(-100, "forum not found");
        return ServiceResponse.buildSuccessResponse(forumMapper.getForumById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addForum(Forum forum)
    {
        forumMapper.addForum(forum);
        return ServiceResponse.buildSuccessResponse(true);
    }

    // 分页
    public ServiceResponse<List<BlogInfo>> getBlogList(Long forumId)
    {
        if (!blogService.ifContainsBlogOfId(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum not found");

        return blogService.getBlogsByForumIdWithTop(forumId);
    }

    public ServiceResponse<List<Forum>> searchForum(String searchText)
    {
        return ServiceResponse.buildSuccessResponse(forumMapper.getForumList().stream().filter(forum -> forum.getName().contains(searchText)).toList());
    }

    public boolean ifContainsForum(Long forumId)
    {
        return forumMapper.ifContainsForum(forumId);
    }
}
