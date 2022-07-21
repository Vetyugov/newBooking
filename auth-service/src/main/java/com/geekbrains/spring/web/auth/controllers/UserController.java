package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.auth.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    @GetMapping("/me")
//    @Operation(
//            summary = "Запрос на получение данных о пользователе",
//            responses = {
//                    @ApiResponse(
//                            description = "Успешный ответ", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = UserDto.class))
//                    )
//            }
//    )
//
//    public UserDto aboutCurrentUser (@RequestHeader String username){
//        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException
//                ("Не удалось найти в базе пользователя с именем " + username));
//        return new UserDto(user.getId(), user.getRole().getId(), user.getUsername(), user.getPassword(), user.getEmail());
//    }

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "201",
            description = "Аутентификая прошла успешно.")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PostMapping
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Пароли не совпадают в окне 'пароль' и 'подтвеждение"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existByEmail(userDto.getEmail())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Адрес электронной почты уже используется"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existByUsername(userDto.getUsername())) {
            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Логин уже существует"), HttpStatus.BAD_REQUEST);
        }
        userService.createOrUpdateUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
