package com.finos.ekyc.demo.service;

import java.util.List;

import com.finos.ekyc.demo.model.User;
import com.finos.ekyc.demo.model.UserDto;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDto update(UserDto userDto);
}
