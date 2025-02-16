package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.dto.CommentInfo;
import org.example.cyberforum.entities.Comment;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void addComment(Comment comment);
    Comment getComment(Long id);
    List<Comment> getCommentsByBlogId(Long id);
    List<Comment> getCommentList();
    void deleteCommentById(Long id);

    List<CommentInfo> getCommentInfoList();
    List<CommentInfo> getCommentInfoListByBlogId(Long blogId);
    List<CommentInfo> getCommentInfoListByBlogIdWithTop(Long blogId);
    List<CommentInfo> getPaginatedCommentInfoListByBlogIdWithTop(Long blogId, int startIndex, int pageSize);

    void deleteCommentByBlogIdAndUserId(Long userId, Long blogId);

    void putTop(Long commentId);

    void cancelTop(Long commentId);

    boolean containsComment(Long commentId);

    Long getCommentCount(Long blogId);
}
