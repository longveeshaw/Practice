package com.practice.springboot.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Object list(String username) {
        return userService.queryForPage(username);

    }

    @RequestMapping("/save")
    public Object save() {
        User user = new User();
        user.setUsername("john");
        userService.insert(user);
        return user;
    }
}
