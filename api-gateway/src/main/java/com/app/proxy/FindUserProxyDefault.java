package com.app.proxy;

import com.app.dto.GetUserDto;
import org.springframework.stereotype.Component;

@Component
public class FindUserProxyDefault implements FindUserProxy {

    @Override
    public GetUserDto findUserByUsername(String username) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("FIND USER BY USERNAME");
        System.out.println("------------------------------------------------------------------");
        return GetUserDto
                .builder()
                .username(username)
                .build();
    }

    @Override
    public GetUserDto findUserById(Long id) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("FIND USER BY ID");
        System.out.println("------------------------------------------------------------------");
        return GetUserDto
                .builder()
                .id(id)
                .build();
    }
}
