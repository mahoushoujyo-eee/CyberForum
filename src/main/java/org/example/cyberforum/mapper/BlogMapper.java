package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
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

    void deleteBlogById(Long id);

    void cancelTop(Long blogId);

    void putTop(Long blogId);
}
