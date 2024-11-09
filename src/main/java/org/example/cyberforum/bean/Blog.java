package org.example.cyberforum.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Blog
{
    private String title;
    private String content;
    private Long id;
    private Long userId;
    private Long forumId;
    private Date createTime;
}