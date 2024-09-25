package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.AuthRequest;
import io.bootify.ngo_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest ) {
        return "User logged in successfully";
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest ) {
     return "User registered successfully";
    }

    @PostMapping("/logout")
    public String logout() {
        return "User logged out successfully";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> email) {
        return "Password reset link sent to email";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> password) {
        return "Password reset successfully";
    }

    @PostMapping("/login/oauth2")
    public String loginOAuth2(@RequestBody Map<String, String> credentials) {
        return "User logged in successfully";
    }



}
