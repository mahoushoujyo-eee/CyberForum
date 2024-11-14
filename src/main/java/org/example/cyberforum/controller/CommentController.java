package org.example.cyberforum.controller;

import org.example.cyberforum.bean.Comment;
import org.example.cyberforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController
{
    @Autowired
    CommentService commentService;

    public String getComment()
    {
        return "comment";
    }

    @RequestMapping("add_comment/{id}")
    public void addComment(@RequestBody Comment comment)
    {
        commentService.addComment(comment);
    }

    @RequestMapping("comment/{id}")
    public List<Comment> getComment(@PathVariable("id") Long blogId)
    {
        return commentService.getCommentsByBlogIdWithTop(blogId);
    }

    @RequestMapping("delete_comment/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") Long commentId)
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
