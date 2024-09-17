package com.example.TaskMeister.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testuser");
    }

    @Test
    void testGetTokenService() {
        String token = jwtService.getTokenService(mockUserDetails);
        assertNotNull(token);
    }

    @Test
    void testGetUsernameFromToken() {
        String token = jwtService.getTokenService(mockUserDetails);
        String username = jwtService.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.getTokenService(mockUserDetails);
        boolean isValid = jwtService.isTokenValid(token, mockUserDetails);
        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired() {
        Map<String, Object> claims = new HashMap<>();
        String expiredToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)) // 2 days old
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)) // expired
                .signWith(SignatureAlgorithm.HS256, jwtService.getKey())
                .compact();

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtService.isTokenValid(expiredToken, mockUserDetails);
        });
    }


    @Test
    void testGetKey() {
        Key key = jwtService.getKey();
        assertNotNull(key);
        assertEquals(Keys.hmacShaKeyFor(jwtService.getKey().getEncoded()).getAlgorithm(), key.getAlgorithm());
    }
}
