package com.habit.management.controller;

import com.habit.management.global.enums.ApiSuccessfullyMessageEnum;
import com.habit.management.global.ApiResponse;
import com.habit.management.model.dto.LoginDto;
import com.habit.management.model.dto.UserDto;
import com.habit.management.service.IUserService;
import com.habit.management.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(IUserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse
                                .<String>builder()
                                .status(HttpStatus.CREATED.value())
                                .message(userService.createUser(userDto))
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );

        String token = jwtUtils.createToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse
                                .<String>builder()
                                .status(HttpStatus.OK.value())
                                .message(ApiSuccessfullyMessageEnum.SUCCESS_LOGIN.getMessage())
                                .data(token)
                                .build()
                );
    }


}
