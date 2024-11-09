package org.example.cyberforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.cyberforum.bean.Blog;

import java.util.List;

@Mapper
public interface BlogMapper
{
    void addBlog(Blog blog);

    Blog getBlog(Long id);

    List<Blog> getBlogList();

    List<Blog> getBlogByForumId(Long id);
}
