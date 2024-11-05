package service;

import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService
{
    @Autowired
    UserMapper userMapper;
}
