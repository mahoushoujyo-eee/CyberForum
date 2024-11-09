package org.example.cyberforum.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
public class User
{
    private Long id;
    private String userName;
    private String password;
    private String email;
}
