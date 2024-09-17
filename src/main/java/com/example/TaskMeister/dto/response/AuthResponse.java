package com.example.TaskMeister.dto.response;

import com.example.TaskMeister.model.ERole;

public class AuthResponse {

    private String token;
    private ERole role;

    // Конструктор без параметрів
    public AuthResponse() {
    }

    // Конструктор з параметрами для token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Конструктор з параметрами для token і role
    public AuthResponse(String token, ERole role) {
        this.token = token;
        this.role = role;
    }

    // Геттер для token
    public String getToken() {
        return token;
    }

    // Сеттер для token
    public void setToken(String token) {
        this.token = token;
    }

    // Геттер для role
    public ERole getRole() {
        return role;
    }

    // Сеттер для role
    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthResponse that = (AuthResponse) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
