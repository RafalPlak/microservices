package com.app.security.service;

import com.app.exception.ApiGatewayException;
import com.app.proxy.FindUserProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FindUserProxy findUserProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findUserProxy.findUserByUsername(username);

        if (user == null) {
            throw new ApiGatewayException("cannot find user");
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
