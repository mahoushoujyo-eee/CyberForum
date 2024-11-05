package org.example.cyberforum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan
@SpringBootApplication
public class CyberForumApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CyberForumApplication.class, args);
    }

}
