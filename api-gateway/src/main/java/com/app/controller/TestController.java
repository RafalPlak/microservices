package com.app.controller;

import com.app.dto.GetUserDto;
import com.app.proxy.FindUserProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final FindUserProxy findUserProxy;

    @GetMapping("/test1")
    public GetUserDto test1() {
        return findUserProxy.findUserById(1L);
    }

    @GetMapping("/test2")
    public GetUserDto test2() {
        return findUserProxy.findUserByUsername("aaa");
    }
}
