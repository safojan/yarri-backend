package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Role;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.model.RegisterRequest;
import io.bootify.ngo_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;


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
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole(roleService.getRole("USER"));

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
