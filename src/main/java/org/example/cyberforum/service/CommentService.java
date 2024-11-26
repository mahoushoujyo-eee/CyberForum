package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.dto.CommentInfo;
import org.example.cyberforum.entities.Comment;
import org.example.cyberforum.mapper.CommentMapper;
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
public class CommentService
{
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;



    @Transactional(rollbackFor = Exception.class)
    public boolean addComment(Comment comment)
    {
        if (comment.getContent().isBlank() || !userService.ifContainsUser(comment.getUserId()) || !blogService.ifContainsBlogOfId(comment.getBlogId()))
            return false;

        comment.setCreateTime(new Date());
        log.info("CommentService addComment: add comment: {}", comment);
        commentMapper.addComment(comment);
        return true;
    }

    public ServiceResponse<List<CommentInfo>> getCommentsByBlogId(Long blogId)
    {
        if (!blogService.ifContainsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");
        List<CommentInfo> comments = commentMapper.getCommentInfoListByBlogId(blogId);
        return ServiceResponse.buildSuccessResponse(comments);
    }

    // 分页
    public ServiceResponse<List<CommentInfo>> getCommentsByBlogIdWithTop(Long blogId)
    {
        if (!blogService.ifContainsBlogOfId(blogId))
            return ServiceResponse.buildErrorResponse(-100, "blog not found");

        List<CommentInfo> comments = commentMapper.getCommentInfoListByBlogId(blogId);
        comments.sort((comment1, comment2) -> comment2.isTop() ? 1 : -1);
        return ServiceResponse.buildSuccessResponse(comments);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> deleteCommentById(Long commentId)
    {
        if (!commentMapper.ifContainsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.deleteCommentById(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> putTop(Long commentId)
    {
        if (!commentMapper.ifContainsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.putTop(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Boolean> cancelTop(Long commentId)
    {
        if (!commentMapper.ifContainsComment(commentId))
            return ServiceResponse.buildErrorResponse(-100, "comment not found");
        commentMapper.cancelTop(commentId);
        return ServiceResponse.buildSuccessResponse(true);
    }
}
