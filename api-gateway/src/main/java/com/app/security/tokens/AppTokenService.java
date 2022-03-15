package com.app.security.tokens;

import com.app.dto.TokensDto;
import com.app.exception.ApiGatewayException;
import com.app.proxy.FindUserProxy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppTokenService {

    @Value("${tokens.access-token.expiration-time-ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.expiration-time-ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.token-property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    private final FindUserProxy findUserProxy;
    private final SecretKey secretKey;

    // ------------------------------------------------------------------------------------------------------------
    // GENEROWANIE TOKENA
    // ------------------------------------------------------------------------------------------------------------
    public TokensDto generateTokens(Authentication authentication) {
        if (authentication == null) {
            throw new ApiGatewayException("authentication object is null");
        }

        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTimeMs);

        var user = findUserProxy.findUserByUsername(authentication.getName());
        var userId = user.getId();
        var userIdAsStr = String.valueOf(user.getId());

        var accessToken = Jwts
                .builder()
                .setSubject(userIdAsStr)
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(userIdAsStr)
                .setExpiration(currentDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // ------------------------------------------------------------------------------------------------------------
    // PARSOWANIE TOKENA
    // ------------------------------------------------------------------------------------------------------------

    public UsernamePasswordAuthenticationToken parseAccessToken(String token) {

        if (token == null) {
            throw new ApiGatewayException("token is null");
        }

        if (!token.startsWith(tokensPrefix)) {
            throw new ApiGatewayException("token has incorrect format");
        }

        var accessToken = token.replace(tokensPrefix, "");
        if (!isTokenValid(accessToken)) {
            throw new ApiGatewayException("token has been expired");
        }

        var userId = Long.parseLong(getId(accessToken));
        var user = findUserProxy.findUserById(userId);
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String getId(String token) {
        return claims(token).getSubject();
    }

    private Date getExpiration(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenValid(String token) {
        return getExpiration(token).after(new Date());
    }
}
