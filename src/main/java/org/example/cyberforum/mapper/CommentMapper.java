package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.Comment;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void addComment(Comment comment);
    Comment getComment(Long id);
    List<Comment> getCommentByBlogId(Long id);
    List<Comment> getCommentList();
}
