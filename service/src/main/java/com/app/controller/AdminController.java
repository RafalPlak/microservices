package com.app.controller;

import com.app.data.AppData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AppData appData;

    @GetMapping("/admins")
    public String admins() {
        return "[" + appData.getAuthor() + ", " + appData.getVersion() + "]: ADMIN";
    }
}
