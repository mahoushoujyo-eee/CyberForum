package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.User;
import org.example.cyberforum.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class AdministratorService
{
    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private BlogService blogService;

    @Autowired
    private ForumService forumService;

    public ServiceResponse<List<User>> getAdministratorByForumId(Long forumId)
    {
        // 统一改成ServiceResponse.buildSuccessResponse/buildErrorResponse();
        if (forumId == null || !forumService.containsForum(forumId))
            ServiceResponse.buildErrorResponse(-100, "forum id is null");

        return ServiceResponse.buildSuccessResponse(administratorMapper.getAdministratorByForumId(forumId));
    }

    // Should be <Boolean>
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addAdministrator(Long forumId, String username)
    {
        Long userId = userService.getUserIdByUserName(username).getData();

        if (userId == null || !forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-1, "user id is null");

        if (administratorMapper.containsAdministrator(forumId, userId))
            return ServiceResponse.buildErrorResponse(-1, "user is already an administrator");

        administratorMapper.addAdministrator(forumId, userId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addAdministrator(Long forumId, Long userId)
    {
        if (userId == null || !forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-1, "user id is null");

        if (administratorMapper.containsAdministrator(forumId, userId))
            return ServiceResponse.buildErrorResponse(-100, "The user is already an administrator");

        administratorMapper.addAdministrator(forumId, userId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    // adminId是不需要的，用forumId+userId就已经够了，在表里能查到就说明是管理员，查不到就不是。
    //是为了检测当前用户是否为管理员，另一id为删除管理员的id
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteAdministrator(Long forumId, Long userId, Long adminId)
    {
        if (!forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        if (!userService.containsUser(userId))
            return ServiceResponse.buildErrorResponse(-100, "user don't exist");

        // 检查是否是该论坛的管理员
        // 没必要加fromXxx
        if (!ifAdministrator(adminId, forumId).getData())
            return ServiceResponse.buildErrorResponse(-100, "user is not an administrator");

        administratorMapper.deleteAdministrator(forumId, userId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<Boolean> ifAdministratorOfBlog(Long blogId, Long userId)
    {
        if (!blogService.containsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog id is null");

        Long forumId = blogService.getForumIdByBlogId(blogId);

        // XxxQueryParam 或者 直接参数展开
        return ifAdministrator(userId, forumId);
    }

    public ServiceResponse<Boolean> ifAdministrator(Long userId, Long forumId)
    {
        if(!userService.containsUser(userId))
            return ServiceResponse.buildErrorResponse(-100, "user don't exist");

        if (!forumService.containsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        return ServiceResponse.buildSuccessResponse(administratorMapper.containsAdministrator(forumId, userId));
    }
}
