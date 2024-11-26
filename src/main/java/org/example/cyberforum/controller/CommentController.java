package org.example.cyberforum.controller;

import org.example.cyberforum.dto.CommentInfo;
import org.example.cyberforum.entities.Comment;
import org.example.cyberforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;

import java.util.List;

@RestController
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @PostMapping("add_comment/{id}")
    public boolean addComment(@RequestBody Comment comment)
    {
        return commentService.addComment(comment);
    }

    @GetMapping("comment/{id}")
    public ServiceResponse<List<CommentInfo>> getComments(@PathVariable("id") Long blogId)
    {
        return commentService.getCommentsByBlogIdWithTop(blogId);
    }

    @DeleteMapping("delete_comment/{comment_id}")
    public ServiceResponse<Boolean> deleteCommentById(@PathVariable("comment_id") Long commentId)
    {
        return commentService.deleteCommentById(commentId);
    }

    @PutMapping("put_comment_top/{comment_id}")
    public ServiceResponse<Boolean> putTop(@PathVariable("comment_id") Long commentId)
    {
        return commentService.putTop(commentId);
    }

    @PutMapping("cancel_comment_top/{comment_id}")
    public ServiceResponse<Boolean> cancelTop(@PathVariable("comment_id") Long commentId)
    {
        return commentService.cancelTop(commentId);
    }
}
