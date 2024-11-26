package org.example.cyberforum.entities;


import lombok.Data;

@Data
public class Forum
{
    private Long id;
    private String name;
    private Long ownerId;
}
