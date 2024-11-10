package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.Blog;

import java.util.List;

@Mapper
public interface BlogMapper
{
    void addBlog(Blog blog);

    Blog getBlogById(Long id);

    List<Blog> getBlogList();
    List<Blog> getLatestBlogList();

    List<Blog> getBlogByForumId(Long id);
}
