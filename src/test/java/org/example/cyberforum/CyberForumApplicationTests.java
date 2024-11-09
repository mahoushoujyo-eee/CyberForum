package org.example.cyberforum;

import org.example.cyberforum.mapper.ForumMapper;
import org.example.cyberforum.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CyberForumApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ForumMapper forumMapper;

    @Test
    void contextLoads()
    {
        System.out.println(forumMapper.getForumList());
    }

}
