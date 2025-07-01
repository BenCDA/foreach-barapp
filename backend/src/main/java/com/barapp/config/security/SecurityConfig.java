package com.barapp.config.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
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
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          // Pas de session ni CSRF pour une API REST stateless
          .csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

          // Déclaration des accès par rôle
          .authorizeHttpRequests(auth -> auth
              // ───────── PUBLIC ─────────
              .requestMatchers(HttpMethod.POST,   "/api/auth/**").permitAll()
              .requestMatchers(HttpMethod.GET,    "/api/cocktails/**").permitAll()
              .requestMatchers(HttpMethod.GET,    "/api/categories/**").permitAll()

              // ───────── CLIENT (=USER) ─────────
              .requestMatchers("/api/cart/**").hasRole("USER")
              .requestMatchers(HttpMethod.POST,   "/api/orders").hasRole("USER")
              .requestMatchers(HttpMethod.GET,    "/api/orders/**").hasRole("USER")

              // ───────── BARMAN ─────────
              .requestMatchers("/api/categories/**").hasRole("BARMAN")
              .requestMatchers("/api/cocktails/**").hasRole("BARMAN")
              .requestMatchers("/api/ingredients/**").hasRole("BARMAN")
              .requestMatchers("/api/cocktail-ingredients/**").hasRole("BARMAN")
              .requestMatchers("/api/cocktail-size-prices/**").hasRole("BARMAN")
              .requestMatchers(HttpMethod.GET,    "/api/orders/to-treat").hasRole("BARMAN")
              .requestMatchers(HttpMethod.PATCH,  "/api/orders/**/status").hasRole("BARMAN")
              .requestMatchers(HttpMethod.PATCH,  "/api/order-cocktails/**/step").hasRole("BARMAN")

              // Toute autre route nécessite authentification
              .anyRequest().authenticated()
          )

          // On branche le provider DAO (UserDetails + BCrypt)
          .authenticationProvider(authenticationProvider())

          // On injecte notre filtre JWT avant UsernamePasswordAuthenticationFilter
          .addFilterBefore(new JwtFilter(jwtTokenProvider),
                           UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Filtre qui extrait le Bearer token, le valide et place l’Authentication dans le contexte.
     */
    private static class JwtFilter extends OncePerRequestFilter {
        private final JwtTokenProvider provider;
        public JwtFilter(JwtTokenProvider provider) {
            this.provider = provider;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest req,
                                        HttpServletResponse res,
                                        FilterChain chain)
                                        throws ServletException, IOException {
            String header = req.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                if (provider.validateToken(token)) {
                    String username = provider.getUsernameFromJWT(token);
                    var auth = new UsernamePasswordAuthenticationToken(
                        username, null, List.of()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(req, res);
        }
    }
}
