package org.example.cyberforum.entities;


import lombok.Data;

@Data
public class Forum
{
    private String name;
    private Long id;
    private Long ownerId;
}
