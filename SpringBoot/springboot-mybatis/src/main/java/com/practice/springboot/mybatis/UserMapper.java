package com.practice.springboot.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where username=#{username}#")
    List<User> queryForPage(String username);

    @Insert("insert into user(username) values(#{username}) ")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void insert(User user);

}
