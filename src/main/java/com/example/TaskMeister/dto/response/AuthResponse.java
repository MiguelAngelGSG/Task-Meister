package com.example.TaskMeister.dto.response;

import com.example.TaskMeister.model.ERole;



public class AuthResponse {
    String token;
    ERole role;

    private AuthResponse(Builder builder) {
        this.token = builder.token;
        this.role = builder.role;
    }


    public String getToken() {
        return token;
    }

    public ERole getRole() {
        return role;
    }

    public static class Builder {
        private String token;
        private ERole role;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setRole(ERole role) {
            this.role = role;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
