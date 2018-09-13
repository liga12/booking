package com.booking.user.controller;

import com.booking.user.service.UserService;
import com.booking.user.transpor.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/registration")
    public  String registration(@RequestBody @Valid  UserCreateDto dto){
        return userService.create(dto);
    }

}
