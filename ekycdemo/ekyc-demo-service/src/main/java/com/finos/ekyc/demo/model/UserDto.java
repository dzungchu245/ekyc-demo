package com.finos.ekyc.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int age;
    private int salary;
}
