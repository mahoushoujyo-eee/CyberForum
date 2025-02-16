package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.dto.BlogInfo;
import org.example.cyberforum.entities.Blog;

import java.util.List;

@Mapper
public interface BlogMapper
{
    void addBlog(Blog blog);

    Blog getBlogById(Long id);

    List<Blog> getBlogList();
    List<Blog> getLatestBlogList();

    List<Blog> getBlogsByForumId(Long id);
    List<BlogInfo> getBlogInfoByForumId(Long id);
    List<BlogInfo> getBlogInfoByForumIdWithTop(Long id);
    List<BlogInfo> getPaginatedBlogInfoByForumIdWithTop(Long id, int startIndex, int pageSize);

    void deleteBlogById(Long id);

    void cancelTop(Long blogId);

    void putTop(Long blogId);

    BlogInfo getBlogInfoById(Long id);

    List<BlogInfo> getLatestBlogInfoList();

    boolean containsBlog(Blog blog);
    boolean containsBlogOfId(Long id);

    List<BlogInfo> getBlogInfoList();

    Long getBlogsCount(Long forumId);
}
