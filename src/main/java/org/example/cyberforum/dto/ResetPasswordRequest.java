package org.example.cyberforum.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest
{
    private String userName;
    private String password;
    private String email;
}
