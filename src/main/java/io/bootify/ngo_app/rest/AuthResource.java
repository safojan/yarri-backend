package io.bootify.ngo_app.rest;
import io.bootify.ngo_app.model.AuthRequest;
import io.bootify.ngo_app.model.AuthResponse;
import io.bootify.ngo_app.model.RegisterRequest;
import io.bootify.ngo_app.security.JwtHelper;
import io.bootify.ngo_app.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;

  @CrossOrigin(origins = "http://127.0.0.1:4200")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticateUser(authRequest.getEmail(), authRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    //registration endpoint with error handling
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userDetailsService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    private Authentication authenticateUser(String username, String password) {

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

}
