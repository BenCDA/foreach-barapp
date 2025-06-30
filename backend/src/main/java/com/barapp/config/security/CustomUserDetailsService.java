package com.barapp.config.security;

import com.barapp.model.User;
import com.barapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        User u = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
            .withUsername(u.getEmail())
            .password(u.getPassword())
            .authorities(u.getRole().name())
            .build();
    }
}
