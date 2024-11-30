package org.example.cyberforum.dto;

import lombok.Data;

@Data
public class IfAdministratorRequest
{
    private Long id;
    private String userName;
    private String password;
    private String email;
    private Long forumId;
    private Long blogId;
}
