package org.example.cyberforum;

import org.example.cyberforum.filter.LogStateFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

@MapperScan
@SpringBootApplication
public class CyberForumApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CyberForumApplication.class, args);
    }

}
