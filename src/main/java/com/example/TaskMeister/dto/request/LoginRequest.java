package com.example.TaskMeister.dto.request;




public class LoginRequest {
    String username;
    String password;

    private LoginRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public static class Builder {
        private String username;
        private String password;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}

