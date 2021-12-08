package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;

/**
 * Created by Vivek on 11/16/21.
 */
public interface UserService {
    UserDto getUserById(Integer userId);
    void saveUser(UserDto userDto);
    List<UserDto> getAllUsers();
}
