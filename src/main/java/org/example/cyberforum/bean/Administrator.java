package org.example.cyberforum.bean;

import lombok.Data;

@Data
public class Administrator
{
    private Long userId;
    private Long forumId;

    private String userName;
}
