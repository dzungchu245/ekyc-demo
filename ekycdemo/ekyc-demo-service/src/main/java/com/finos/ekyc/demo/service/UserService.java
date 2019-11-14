package com.finos.ekyc.demo.service;

import java.util.List;

import com.finos.ekyc.demo.model.UserDto;

public interface UserService {

    UserDto save(UserDto user);
    List<UserDto> findAll();
    void delete(Long id);

    UserDto findOne(String username);

    UserDto findById(Long id);

    UserDto update(UserDto userDto);
}
