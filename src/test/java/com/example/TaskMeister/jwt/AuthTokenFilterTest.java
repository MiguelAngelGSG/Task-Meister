package com.example.TaskMeister.jwt;

import com.example.TaskMeister.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthTokenFilterTest {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private AuthTokenFilter authTokenFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userDetailsService = mock(UserDetailsService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        userDetails = mock(UserDetails.class);

        authTokenFilter = new AuthTokenFilter(jwtService, userDetailsService);

        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_withValidToken() throws ServletException, IOException {
        String token = "valid-jwt-token";
        String username = "testuser";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that the authentication token is correctly set in the SecurityContext
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authToken);
        assertEquals(userDetails, authToken.getPrincipal());
        assertNull(authToken.getCredentials());

        // Ensure filterChain.doFilter is still called
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_withInvalidToken() throws ServletException, IOException {
        String token = "invalid-jwt-token";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(jwtService.getUsernameFromToken(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);  // Token is invalid

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that SecurityContextHolder is not updated with invalid token
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Ensure filterChain.doFilter is still called
        verify(filterChain).doFilter(request, response);
    }


    @Test
    void testDoFilterInternal_withNoToken() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);  // No token

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that SecurityContextHolder remains empty
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Ensure filterChain.doFilter is called even when no token is provided
        verify(filterChain).doFilter(request, response);
    }

}
