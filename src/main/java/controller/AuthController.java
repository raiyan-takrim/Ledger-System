package controller;

import service.AuthService;

public class AuthController {
    private AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }
    public boolean register(String name, String email, String password) {
        return authService.register(name, email, password);
    }
    public boolean login(String email, String password) {
        return authService.login(email, password);
    }
    public void logout() {
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }
}