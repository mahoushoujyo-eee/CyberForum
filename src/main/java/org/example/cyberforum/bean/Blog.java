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
    private String username;
    private Long forumId;
    private String forumName;
    private Date createTime;
    private boolean isTop;
}
