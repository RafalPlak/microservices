package com.app.controller;

import com.app.data.AppData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final AppData appData;

    @GetMapping("/users")
    @HystrixCommand(fallbackMethod = "usersFaultTolerance", ignoreExceptions = IllegalArgumentException.class)
    public String users() {

        if (Math.random() > 0.2) {
            throw new IllegalStateException("USERS ERROR");
        }

        return "[" + appData.getAuthor() + ", " + appData.getVersion() + "]: USER";
    }

    @HystrixCommand(fallbackMethod = "usersFaultTolerance2")
    public String usersFaultTolerance() {

        if (Math.random() > 0.2) {
            throw new IllegalStateException("USERS 2 ERROR");
        }

        return "DEFAULT DATA FOR USERS";
    }

    public String usersFaultTolerance2() {

        return "DEFAULT DATA FOR USERS 2";
    }
}
