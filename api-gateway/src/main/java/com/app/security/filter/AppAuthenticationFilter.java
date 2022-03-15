package com.app.security.filter;

import com.app.dto.AuthenticationDto;
import com.app.exception.ApiGatewayException;
import com.app.security.tokens.AppTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppTokenService appTokenService;

    public AppAuthenticationFilter(AuthenticationManager authenticationManager, AppTokenService appTokenService) {
        this.authenticationManager = authenticationManager;
        this.appTokenService = appTokenService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            var authenticationDto
                    = new ObjectMapper().readValue(request.getInputStream(), AuthenticationDto.class);
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationDto.getUsername(),
                            authenticationDto.getPassword(),
                            Collections.emptyList()
                    ));
        } catch (Exception e) {
            throw new ApiGatewayException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        var tokens = appTokenService.generateTokens(authResult);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
