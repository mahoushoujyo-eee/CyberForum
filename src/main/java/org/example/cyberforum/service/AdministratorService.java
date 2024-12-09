package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.Administrator;
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
        ServiceResponse<List<User>> response = new ServiceResponse<>();
        response.setSuccess(true);

        if (forumId == null || !forumService.ifContainsForum(forumId))
            response.setSuccess(false);

        response.setData(administratorMapper.getAdministratorByForumId(forumId));
        return response;
    }

    // Should be <Boolean>
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<String> addAdministrator(Long forumId, String username)
    {
        ServiceResponse<String> response = new ServiceResponse<>();

        Long userId = userService.getUserIdByUserName(username).getData();

        if (userId == null || !forumService.ifContainsForum(forumId))
            return ServiceResponse.buildErrorResponse(-1, "user id is null");

        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        administrator.setForumId(forumId);

        if (administratorMapper.ifContainsAdministrator(administrator))
        {
            response.setSuccess(false);
            response.setMessage("user is already an administrator");
            return response;
        }

        administratorMapper.addAdministrator(forumId, userId);
        response.setSuccess(true);
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<String> addAdministrator(Long forumId, Long userId)
    {
        ServiceResponse<String> response = new ServiceResponse<>();

        if (userId == null || !forumService.ifContainsForum(forumId))
            return ServiceResponse.buildErrorResponse(-1, "user id is null");

        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        administrator.setForumId(forumId);

        if (administratorMapper.ifContainsAdministrator(administrator))
        {
            response.setSuccess(false);
            response.setMessage("The user is already an administrator");
            return response;
        }

        administratorMapper.addAdministrator(forumId, userId);
        response.setSuccess(true);
        return response;
    }

    // adminId是不需要的，用forumId+userId就已经够了，在表里能查到就说明是管理员，查不到就不是。
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteAdministrator(Long forumId, Long userId, Long adminId)
    {
        if (!forumService.ifContainsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        if (!userService.ifContainsUser(userId))
            return ServiceResponse.buildErrorResponse(-100, "user don't exist");

        // 检查是否是该论坛的管理员
        // 没必要加fromXxx
        if (!ifAdministratorFromForum(adminId, forumId).getData())
        {
            ServiceResponse<Boolean> response = new ServiceResponse<>();
            response.setSuccess(false);
            response.setMessage("user is not an administrator");
            return response;
        }

        administratorMapper.deleteAdministrator(forumId, userId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<Boolean> ifAdministratorFromBlog(Long blogId, Long userId)
    {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        response.setSuccess(true);

        if (!blogService.ifContainsBlogOfId(blogId))
        {
            response.setSuccess(false);
            response.setMessage("blog id is null");
            return response;
        }

        if(!userService.ifContainsUser(userId))
        {
            response.setSuccess(false);
            response.setMessage("user id is null");
            return response;
        }
        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        administrator.setForumId(blogService.getForumIdByBlogId(blogId));

        // XxxQueryParam 或者 直接参数展开
        response.setData(administratorMapper.ifContainsAdministrator(administrator));
        return response;
    }

    public ServiceResponse<Boolean> ifAdministratorFromForum(Long userId, Long forumId)
    {
        if(!userService.ifContainsUser(userId))
            return ServiceResponse.buildErrorResponse(-100, "user don't exist");

        if (!forumService.ifContainsForum(forumId))
            return ServiceResponse.buildErrorResponse(-100, "forum don't exist");

        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        administrator.setForumId(forumId);

        return ServiceResponse.buildSuccessResponse(administratorMapper.ifContainsAdministrator(administrator));
    }
}
