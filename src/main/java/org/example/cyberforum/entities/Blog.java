package org.example.cyberforum.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Blog
{
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long forumId;
    private Date creationTime;
    private boolean isTop;
}
