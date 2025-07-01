package com.barapp.config.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/api/auth/**").permitAll()
              .anyRequest().authenticated()
          )
          .userDetailsService(userDetailsService)
          .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Filtre qui lit l'en-tête Authorization, valide le JWT et peuple le contexte
    private static class JwtFilter extends OncePerRequestFilter {
        private final JwtTokenProvider provider;
        public JwtFilter(JwtTokenProvider provider) { this.provider = provider; }

        @Override
        protected void doFilterInternal(HttpServletRequest req,
                                        HttpServletResponse res,
                                        FilterChain chain) throws ServletException, IOException {
            String header = req.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                if (provider.validateToken(token)) {
                    String username = provider.getUsernameFromJWT(token);
                    var authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        username, null, List.of()
                    );
                    org.springframework.security.core.context.SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
                }
            }
            chain.doFilter(req, res);
        }
    }

    
}
