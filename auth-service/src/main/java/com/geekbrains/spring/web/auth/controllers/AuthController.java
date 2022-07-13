package com.geekbrains.spring.web.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.auth.dto.JwtRequest;
import com.geekbrains.spring.web.auth.dto.JwtResponse;
import com.geekbrains.spring.web.auth.services.UserService;
import com.geekbrains.spring.web.auth.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping
@Tag(name = "API для работы с сервисом аутентификации")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Авторизация пользователя")
    //@PostMapping
    @ApiResponse(responseCode = "200", description = "Авторизация выполнена успешно.",
            headers = @Header(name = "Authorization", description = "Токен пользователя"))
    @ApiResponse(responseCode = "400", description = "Ошибочный запрос",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @ApiResponse(responseCode = "401", description = "Авторизация выполнена ошибочно",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))

    @PostMapping("/api/v1/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("AUTH_SERVICE_INCORRECT_USERNAME_OR_PASSWORD", "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(
                token,
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0))
        );
    }
}
