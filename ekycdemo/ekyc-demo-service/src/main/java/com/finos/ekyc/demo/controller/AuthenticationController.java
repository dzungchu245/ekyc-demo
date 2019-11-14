package com.finos.ekyc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finos.ekyc.demo.config.JwtTokenUtil;
import com.finos.ekyc.demo.model.ApiResponse;
import com.finos.ekyc.demo.model.LoginUser;
import com.finos.ekyc.demo.model.UserDto;
import com.finos.ekyc.demo.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/generate-token")
    public ApiResponse<UserDto> register(@RequestBody LoginUser loginUser) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final UserDto user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        user.setToken(token);
        return new ApiResponse<>(200, "success", user);
    }

}
