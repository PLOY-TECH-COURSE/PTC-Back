package org.plteco.ploytechcourse.shared.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.logout.CustomLogoutFilter;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.JwtFilter;
import org.plteco.ploytechcourse.domain.user.login.service.LoginFilter;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/email", "/signup", "/login", "/refresh","/verify").permitAll()
                        .requestMatchers(HttpMethod.GET, "/documents", "/users/search/{user-name}", "/documents/search", "/comments/{document-id}", "/favorites", "/announcements").permitAll()
                        .requestMatchers(HttpMethod.POST, "/logout", "/comments/{document-id}", "/favorites/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{user-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/applications").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/applications", "/permissions").hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/documents", "/comments/{document-id}/{comment-id}").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/documents", "/likes/{document-id}").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/likes/{document-id}").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/S3").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")
                        .anyRequest().denyAll()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        //클라이언트 주소 넣어야함
                        configuration.setAllowedOrigins(Collections.singletonList("https://ptc-front-bves.vercel.app"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(), jwtUtil,refreshRepository), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        return http.build();
    }
}