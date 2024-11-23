package org.example.cyberforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cyberforum.entities.Comment;
import org.example.cyberforum.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentService
{
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;



    @Transactional(rollbackFor = Exception.class)
    public boolean addComment(Comment comment)
    {
        if (comment.getContent().isBlank() || !userService.ifContainsUser(comment.getUserId()) || !blogService.ifContainsBlogOfId(comment.getBlogId()))
            return false;

        comment.setCreateTime(new Date());
        log.info("CommentService addComment: add comment: " + comment);
        commentMapper.addComment(comment);
        return true;
    }

    public List<Comment> getCommentsByBlogId(Long blogId)
    {
        List<Comment> comments = commentMapper.getCommentsByBlogId(blogId);
        for (Comment comment: comments)
        {
            comment.setUsername(userService.getUserById(comment.getUserId()).getUserName());
        }
        return comments;
    }

    // 分页
    public List<Comment> getCommentsByBlogIdWithTop(Long blogId)
    {
        List<Comment> comments = getCommentsByBlogId(blogId);
        comments.sort((comment1, comment2) -> comment2.isTop() ? 1 : -1);
        return comments;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentById(Long commentId)
    {
        commentMapper.deleteCommentById(commentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void putTop(Long commentId)
    {
        commentMapper.putTop(commentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelTop(Long commentId)
    {
        commentMapper.cancelTop(commentId);
    }
}
