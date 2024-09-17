package com.example.TaskMeister.dto.request;
import com.example.TaskMeister.model.ERole;


public class RegisterRequest {
    String username;
    String password;
    ERole role;


    private RegisterRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ERole getRole() {
        return role;
    }

    public static class Builder {
        private String username;
        private String password;
        private ERole role;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(ERole role) {
            this.role = role;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }

}
