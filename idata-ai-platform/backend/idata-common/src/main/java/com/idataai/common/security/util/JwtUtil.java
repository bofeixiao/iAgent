package com.idataai.common.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author iDataAI
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * JWT密钥
     */
    @Value("${jwt.secret:iDataAI2026SecretKeyForJWTTokenGenerationAndValidation}")
    private String secret;

    /**
     * Token有效期(毫秒) - 默认7天
     */
    @Value("${jwt.expiration:604800000}")
    private Long expiration;

    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @return Token
     */
    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return generateToken(claims);
    }

    /**
     * 生成Token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return Token
     */
    public String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        return generateToken(claims);
    }

    /**
     * 生成Token
     *
     * @param claims 自定义声明
     * @return Token
     */
    public String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 从Token中获取Claims
     *
     * @param token Token
     * @return Claims
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Failed to parse JWT token", e);
            return null;
        }
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            Object userId = claims.get("userId");
            if (userId instanceof Integer) {
                return ((Integer) userId).longValue();
            } else if (userId instanceof Long) {
                return (Long) userId;
            }
        }
        return null;
    }

    /**
     * 从Token中获取用户名
     *
     * @param token Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? (String) claims.get("username") : null;
    }

    /**
     * 验证Token是否有效
     *
     * @param token Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return false;
            }
            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return false;
        }
    }

    /**
     * 检查Token是否即将过期(小于1小时)
     *
     * @param token Token
     * @return 是否即将过期
     */
    public boolean isTokenExpiringSoon(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return true;
            }
            Date expiration = claims.getExpiration();
            if (expiration == null) {
                return true;
            }
            long timeLeft = expiration.getTime() - System.currentTimeMillis();
            return timeLeft < 3600000; // 小于1小时
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 刷新Token
     *
     * @param token 旧Token
     * @return 新Token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        Map<String, Object> newClaims = new HashMap<>(claims);
        newClaims.remove("iat");
        newClaims.remove("exp");
        return generateToken(newClaims);
    }

    /**
     * 获取密钥
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
