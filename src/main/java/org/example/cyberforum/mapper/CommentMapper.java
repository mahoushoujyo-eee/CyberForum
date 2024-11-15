package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.Comment;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void addComment(Comment comment);
    Comment getComment(Long id);
    List<Comment> getCommentsByBlogId(Long id);
    List<Comment> getCommentList();
    void deleteCommentById(Long id);

    void deleteCommentByBlogIdAndUserId(Long userId, Long blogId);

    void putTop(Long commentId);

    void cancelTop(Long commentId);
}
