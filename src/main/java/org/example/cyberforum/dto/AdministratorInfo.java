package org.example.cyberforum.dto;

import lombok.Data;

import java.util.LongSummaryStatistics;

@Data
public class AdministratorInfo
{
    private Long id;
    private Long userId;
    private Long forumId;
    private String userName;
}
