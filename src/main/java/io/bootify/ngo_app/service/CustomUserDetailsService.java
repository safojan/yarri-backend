package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Role;
import io.bootify.ngo_app.domain.User;

import io.bootify.ngo_app.model.RegisterRequest;
import io.bootify.ngo_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Convert roles to authorities
        Set<SimpleGrantedAuthority> authorities = user.getRole().getRoleUsers().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toCollection(HashSet::new));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
    public void register(RegisterRequest registerRequest) {
        // Check if user already exists
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }
        //new user with role as user
        User user = new User();
        Role role = roleService.getRole("Admin");
        user.setEmail(registerRequest.getEmail());
        //encrypt password using bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword =bCryptPasswordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);
        user.setPhone(registerRequest.getPhone());
        user.setName(registerRequest.getName());
       // userRepository.save(user);
        System.out.println(user.getRole().getId());
        userRepository.save(user);
    }
    public String getRole(String email){
        User user = userRepository.findByEmail(email);
        return user.getRole().getName();
    }
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }
}


