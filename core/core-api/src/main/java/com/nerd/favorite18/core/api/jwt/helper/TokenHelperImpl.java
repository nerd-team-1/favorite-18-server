package com.nerd.favorite18.core.api.jwt.helper;

import com.nerd.favorite18.core.api.jwt.model.Token;
import com.nerd.favorite18.core.api.support.error.CoreApiException;
import com.nerd.favorite18.core.api.support.error.ErrorType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenHelperImpl implements TokenHelper {
    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public Token issueAccessToken(Map<String, Object> data) {
        final LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        return getTokenDto(data, expiredLocalDateTime);
    }

    @Override
    public Token issueRefreshToken(Map<String, Object> data) {
        final LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        return getTokenDto(data, expiredLocalDateTime);
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        final JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

        try {
            final Jws<Claims> result = parser.parseClaimsJws(token);

            return new HashMap<>(result.getBody());
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                // 토큰이 유효하지 않을 때
                throw new CoreApiException(ErrorType.INVALID_TOKEN);
            } else if (e instanceof ExpiredJwtException) {
                // 만료된 토큰
                throw new CoreApiException(ErrorType.EXPIRED_TOKEN);
            } else {
                // 그 외
                throw new CoreApiException(ErrorType.TOKEN_EXCEPTION);
            }
        }
    }

    private Token getTokenDto(Map<String, Object> data, LocalDateTime expiredLocalDateTime) {
        var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return Token.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }
}
