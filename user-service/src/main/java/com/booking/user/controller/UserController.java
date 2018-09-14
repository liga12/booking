package com.booking.user.controller;

import com.booking.user.service.UserService;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserOutcomeDto> getAll(UserFindDto dto, Pageable pageable) {
        return userService.getAll(dto, pageable);
    }

    @PutMapping("/registration")
    public String registration(@RequestBody @Valid UserCreateDto dto) {
        return userService.create(dto);
    }

}
