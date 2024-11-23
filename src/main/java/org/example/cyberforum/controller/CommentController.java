package org.example.cyberforum.controller;

import org.example.cyberforum.entities.Comment;
import org.example.cyberforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController
{
    @Autowired
    CommentService commentService;

    @PostMapping("add_comment/{id}")
    public boolean addComment(@RequestBody Comment comment)
    {
        return commentService.addComment(comment);
    }

    @GetMapping("comment/{id}")
    public List<Comment> getComments(@PathVariable("id") Long blogId)
    {
        return commentService.getCommentsByBlogIdWithTop(blogId);
    }

    @DeleteMapping("delete_comment/{comment_id}")
    public void deleteCommentById(@PathVariable("comment_id") Long commentId)
    {
        commentService.deleteCommentById(commentId);
    }

    @PutMapping("put_comment_top/{comment_id}")
    public void putTop(@PathVariable("comment_id") Long commentId)
    {
        commentService.putTop(commentId);
    }

    @PutMapping("cancel_comment_top/{comment_id}")
    public void cancelTop(@PathVariable("comment_id") Long commentId)
    {
        commentService.cancelTop(commentId);
    }
}
