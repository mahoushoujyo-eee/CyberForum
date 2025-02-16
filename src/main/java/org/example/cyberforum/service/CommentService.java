package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.CommentInfo;
import org.example.cyberforum.entities.Comment;
import org.example.cyberforum.mapper.CommentMapper;
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
public class CommentService
{
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    private static final int PAGE_SIZE = 10; //

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> addComment(Comment comment)
    {
        if (comment.getContent().isBlank())
            return ServiceResponse.buildErrorResponse(-100, "comment content is blank");
        else if (!userService.containsUser(comment.getUserId()))
            return ServiceResponse.buildErrorResponse(-100, "user not found");
        else if (!blogService.containsBlogOfId(comment.getBlogId()))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        log.info("CommentService addComment: add comment: {}", comment); // JSON.
        commentMapper.addComment(comment);
        return ServiceResponse.buildSuccessResponse(true);
    }

    public ServiceResponse<List<CommentInfo>> getCommentsByBlogId(Long blogId)
    {
        if (!blogService.containsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");
        List<CommentInfo> comments = commentMapper.getCommentInfoListByBlogId(blogId);
        return ServiceResponse.buildSuccessResponse(comments);
    }

    public ServiceResponse<List<CommentInfo>> getCommentsByBlogIdWithTop(Long blogId)
    {
        if (!blogService.containsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        List<CommentInfo> comments = commentMapper.getCommentInfoListByBlogIdWithTop(blogId);
        return ServiceResponse.buildSuccessResponse(comments);
    }

    public ServiceResponse<PaginatedData<CommentInfo>> getCommentsByBlogIdWithTop(Long blogId, int pageIndex, int pageSize)
    {
        if (!blogService.containsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        if (pageIndex < 1)
            return ServiceResponse.buildErrorResponse(-100, "page index cannot be negative");

        List<CommentInfo> comments = commentMapper.getPaginatedCommentInfoListByBlogIdWithTop(blogId, (pageIndex - 1) * PAGE_SIZE, PAGE_SIZE);
        Long commentCount = commentMapper.getCommentCount(blogId);

        PaginatedData<CommentInfo> paginatedData = new PaginatedData<>();
        paginatedData.setData(comments);
        paginatedData.setCurrent(pageIndex);
        paginatedData.setPageSize(pageSize);
        paginatedData.setPageCount((int) Math.ceil(commentCount / (float)pageSize));
        paginatedData.setTotal(commentCount);

        if (pageIndex > paginatedData.getPageCount())
            return ServiceResponse.buildErrorResponse(-100, "page index out of range");

        return ServiceResponse.buildSuccessResponse(paginatedData);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteCommentById(Long commentId)
    {
        if (!commentMapper.containsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.deleteCommentById(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> putTop(Long commentId)
    {
        if (!commentMapper.containsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.putTop(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> cancelTop(Long commentId)
    {
        if (!commentMapper.containsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.cancelTop(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }
}
