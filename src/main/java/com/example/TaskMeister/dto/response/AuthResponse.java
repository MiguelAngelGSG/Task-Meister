package com.example.TaskMeister.dto.response;

import com.example.TaskMeister.model.ERole;

public class AuthResponse {

    private String token;
    private ERole role;


    public AuthResponse() {
    }


    public AuthResponse(String token) {
        this.token = token;
    }


    public AuthResponse(String token, ERole role) {
        this.token = token;
        this.role = role;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public ERole getRole() {
        return role;
    }


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
