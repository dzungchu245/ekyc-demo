package com.finos.ekyc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private String token;
    private String username;

    public AuthToken(String token){
        this.token = token;
    }
}
