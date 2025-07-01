package com.barapp.config.security;

import java.io.IOException;
import java.util.List;

import com.barapp.model.User;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                // PUBLIC
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cocktails/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                // CLIENT

                // CLIENT
                .requestMatchers(HttpMethod.POST, "/api/cart/").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.POST, "/api/cart/add").hasAuthority("ROLE_CLIENT")
                .requestMatchers("/api/cart/**").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/cart/").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("ROLE_CLIENT")
                // BARMAN
                .requestMatchers("/api/categories/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers("/api/cocktails/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers("/api/ingredients/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers("/api/cocktail-ingredients/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers("/api/cocktail-size-prices/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.GET, "/api/orders/to-treat").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/orders/**/status").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/order-cocktails/**/step").hasAuthority("ROLE_BARMAN")
                // AUTH pour le reste
                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

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
     * Filtre JWT personnalisé avec logs de debug
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
                    String email = provider.getEmailFromToken(token);
                    String role = provider.getRoleFromToken(token);

                    System.out.println("✅ JWT détecté : " + token);
                    System.out.println("➡️ Utilisateur : " + email);
                    System.out.println("➡️ Rôle brut du token : " + role);

                    var auth = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    System.out.println("❌ JWT invalide !");
                }
            } else {
                System.out.println("ℹ️ Aucun token Bearer fourni.");
            }

            chain.doFilter(req, res);
        }
    }
}
