package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    @GetMapping("/me")
    @Operation(
            summary = "Запрос на получение данных о пользователе",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )

    public ProfileDto aboutCurrentUser (@RequestHeader String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException
                ("Не удалось найти в базе пользователя с именем " + username));
        return new ProfileDto(user.getId(), user.getRole().getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "201",
            description = "Аутентификая прошла успешно.")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PostMapping("/reg/")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody ProfileDto profileDto) {
        if (!profileDto.getPassword().equals(profileDto.getPasswordConfirmation())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Пароли не совпадают в окне 'пароль' и 'подтвеждение"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existByEmail(profileDto.getEmail())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Адрес электронной почты уже используется"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existByUsername(profileDto.getUsername())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Логин уже существует"), HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(profileDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
