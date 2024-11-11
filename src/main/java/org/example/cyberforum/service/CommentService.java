package org.example.cyberforum.service;

import org.example.cyberforum.bean.Comment;
import org.example.cyberforum.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService
{
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    public void addComment(Comment comment)
    {
        comment.setCreateTime(new Date());
        log.info("CommentService addComment: add comment: " + comment);
        commentMapper.addComment(comment);
    }

    public List<Comment> getCommentByBlogId(Long blogId)
    {
        List<Comment> comments = commentMapper.getCommentByBlogId(blogId);
        for (Comment comment: comments)
        {
            comment.setUsername(userService.getUserById(comment.getUserId()).getUserName());
        }
        return comments;
    }
}
