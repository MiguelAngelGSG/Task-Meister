package com.example.TaskMeister.dto.request;


import com.example.TaskMeister.model.ERole;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private ERole role;

    // Конструктор без параметрів
    public RegisterRequest() {
    }

    // Конструктор з параметрами
    public RegisterRequest(String username, String email, String password, ERole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Геттер для username
    public String getUsername() {
        return username;
    }

    // Сеттер для username
    public void setUsername(String username) {
        this.username = username;
    }

    // Геттер для email
    public String getEmail() {
        return email;
    }

    // Сеттер для email
    public void setEmail(String email) {
        this.email = email;
    }

    // Геттер для password
    public String getPassword() {
        return password;
    }

    // Сеттер для password
    public void setPassword(String password) {
        this.password = password;
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
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterRequest that = (RegisterRequest) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
