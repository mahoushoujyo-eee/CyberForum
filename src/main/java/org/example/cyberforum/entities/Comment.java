package org.example.cyberforum.entities;


import lombok.Data;

import java.util.Date;
@Data
public class Comment
{
    private Long id;
    private String content;
    private Long userId;
    private Long blogId;
    private Date creationTime;
    private boolean isTop;
}
