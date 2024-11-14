package org.example.cyberforum.bean;


import lombok.Data;

import java.util.Date;
@Data
public class Comment
{
    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long blogId;
    private Date createTime;
    private boolean isTop;
}
