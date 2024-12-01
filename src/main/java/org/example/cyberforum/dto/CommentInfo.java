package org.example.cyberforum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentInfo
{
    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long blogId;
    private Date creationTime;
    private boolean isTop;
}
