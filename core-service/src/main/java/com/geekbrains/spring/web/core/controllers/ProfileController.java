//package com.geekbrains.spring.web.core.controllers;
//
//import com.geekbrains.spring.web.api.core.UserDto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/user")
//public class UserController {
//    @GetMapping("/{username}")
//    @Operation(
//            summary = "Запрос на получение данных о пользователе",
//            responses = {
//                    @ApiResponse(
//                            description = "Успешный ответ", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = UserDto.class))
//                    )
//            }
//    )
//    public UserDto getCurrentUserInfo(@RequestHeader String username) {
//        return new UserDto(username);
//    }
//}
