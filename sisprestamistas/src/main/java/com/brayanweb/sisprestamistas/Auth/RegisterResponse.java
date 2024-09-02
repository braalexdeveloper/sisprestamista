package com.brayanweb.sisprestamistas.Auth;

public class RegisterResponse {

    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
