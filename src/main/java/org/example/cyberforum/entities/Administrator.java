package org.example.cyberforum.entities;

import lombok.Data;

@Data
public class Administrator
{
    private Long userId;
    private Long forumId;

    // Delete this column.
    private String userName;
}
