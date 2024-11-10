package org.example.cyberforum.controller;

import org.example.cyberforum.bean.Comment;
import org.example.cyberforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return commentService.getCommentByBlogId(blogId);
    }
}
