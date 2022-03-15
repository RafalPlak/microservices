package com.app.security.filter;

import com.app.security.tokens.AppTokenService;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppAuthorizationFilter extends BasicAuthenticationFilter {

    private final AppTokenService appTokenService;

    public AppAuthorizationFilter(AuthenticationManager authenticationManager, AppTokenService appTokenService) {
        super(authenticationManager);
        this.appTokenService = appTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken != null) {
            var auth = appTokenService.parseAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
