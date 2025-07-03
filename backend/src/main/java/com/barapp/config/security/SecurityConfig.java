package com.barapp.config.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.barapp.config.security.CustomUserDetailsService;
import com.barapp.config.security.JwtTokenProvider;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1) CORS + CSRF off + Stateless
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 2) Règles d’accès
            .authorizeHttpRequests(auth -> auth
                // Pré-flight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Endpoints publics
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                // ---------- CRUD COCKTAILS pour BARMAN (ordre important !) ----------
                .requestMatchers(HttpMethod.DELETE, "/api/cocktails/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PUT, "/api/cocktails/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/cocktails/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.POST, "/api/cocktails").hasAuthority("ROLE_BARMAN")

                // ---------- GET COCKTAILS public (pour tous les users, y compris non connectés) ----------
                .requestMatchers(HttpMethod.GET, "/api/cocktails/**").permitAll()

                // Commandes CLIENT
                .requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("ROLE_CLIENT")
                // Panier CLIENT
                .requestMatchers("/api/cart/**").hasAuthority("ROLE_CLIENT")

                // Tailles accessibles aux CLIENT et BARMAN
                .requestMatchers(HttpMethod.GET, "/api/sizes/**")
                    .hasAnyAuthority("ROLE_CLIENT", "ROLE_BARMAN")
                // Opérations sizes réservées au BARMAN
                .requestMatchers(HttpMethod.POST, "/api/sizes/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PUT, "/api/sizes/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/sizes/**").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.DELETE, "/api/sizes/**").hasAuthority("ROLE_BARMAN")

                // Endpoints BARMAN sur catégories
                .requestMatchers("/api/categories/**").hasAuthority("ROLE_BARMAN")

                // Lecture ingrédients pour un cocktail (CLIENT & BARMAN)
                .requestMatchers(HttpMethod.GET, "/api/cocktail-ingredients/by-cocktail/**")
                    .hasAnyAuthority("ROLE_CLIENT", "ROLE_BARMAN")
                // CRUD ingrédients (BARMAN)
                .requestMatchers("/api/cocktail-ingredients/**").hasAuthority("ROLE_BARMAN")

                // Lecture prix pour un cocktail (CLIENT & BARMAN)
                .requestMatchers(HttpMethod.GET, "/api/cocktail-size-prices/by-cocktail/**")
                    .hasAnyAuthority("ROLE_CLIENT", "ROLE_BARMAN")
                // CRUD prix (BARMAN)
                .requestMatchers("/api/cocktail-size-prices/**").hasAuthority("ROLE_BARMAN")

                // Commandes à traiter / mise à jour (BARMAN)
                .requestMatchers(HttpMethod.GET, "/api/orders/to-treat").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/orders/*/status").hasAuthority("ROLE_BARMAN")
                .requestMatchers(HttpMethod.PATCH, "/api/order-cocktails/*/step").hasAuthority("ROLE_BARMAN")

                // Tout le reste -> authentifié
                .anyRequest().authenticated()
            )
            // 3) Provider + filtre JWT
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
     * Filtre JWT : extrait et valide le token.
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
                    var auth = new UsernamePasswordAuthenticationToken(
                            email, null, List.of(new SimpleGrantedAuthority(role))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(req, res);
        }
    }

    /**
     * Bean CORS global.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
