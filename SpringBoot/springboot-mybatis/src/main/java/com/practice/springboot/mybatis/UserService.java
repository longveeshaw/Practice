package com.practice.springboot.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> queryForPage(String username) {
      return   userMapper.queryForPage(username);
    }


    @Transactional
    public void insert(User user) {
        userMapper.insert(user);
    }

}
