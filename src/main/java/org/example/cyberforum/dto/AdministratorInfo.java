package org.example.cyberforum.dto;

import lombok.Data;

@Data
public class AdministratorInfo
{
    private Long userId;
    private Long forumId;
    private String userName;
}
