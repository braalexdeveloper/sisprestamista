package com.brayanweb.sisprestamistas.Auth;

public class AuthResponse {

    private String token;
    private String userName;
    private String email;
    private String role;

    private AuthResponse(Builder builder) {
        this.token = builder.token;
        this.userName = builder.userName;
        this.email = builder.email;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String token;
        private String userName;
        private String email;
        private String role;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
