package org.example.cyberforum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan
@SpringBootApplication(scanBasePackages = {"org.example.cyberforum", "stark.dataworks.boot.autoconfig"})
@EnableTransactionManagement
public class CyberForumApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CyberForumApplication.class, args);
    }
}
