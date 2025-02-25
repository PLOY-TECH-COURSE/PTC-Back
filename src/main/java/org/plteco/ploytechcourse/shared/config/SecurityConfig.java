package org.plteco.ploytechcourse.shared.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.logout.CustomLogoutFilter;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.plteco.ploytechcourse.domain.jwt.service.JwtFilter;
import org.plteco.ploytechcourse.domain.user.login.service.LoginFilter;
import org.plteco.ploytechcourse.domain.jwt.service.JwtUtil;
import org.plteco.ploytechcourse.shared.exception.CustomAccessDeniedHandler;
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

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-resources/**","/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/documents", "/documents/{document-id}", "/users/search/{user-name}", "/documents/search", "/comments/{document-id}", "/favorites", "/announcements", "/announcements/{announcement-id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/logout", "/comments/{document-id}", "/favorites/{document-id}", "/documents").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.GET, "/users/{user-id}","mypage").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/applications").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/applications").hasAnyRole("ADMIN","SUPERADMIN")

                        .requestMatchers(HttpMethod.POST,"/accept").hasAnyRole("ADMIN","SUPERADMIN")

                        .requestMatchers(HttpMethod.POST,"/announcements").hasAnyRole("ADMIN","SUPERADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/announcements").hasAnyRole("ADMIN","SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/announcements").hasAnyRole("ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.GET, "/permissions").hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/permissions").hasRole("SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/tech-course","/class","/track").hasAnyRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/tech-course","/class","/class/{generation}","/track","/track/{id}").permitAll()

                        .requestMatchers(HttpMethod.PATCH, "/documents").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/documents").hasAnyRole("STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/S3","/profile").hasAnyRole("USER","STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/document/comments/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/document/comments/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/document/comments/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/document/comments/{document-id}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/announcement/comments/{announcement-id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/announcement/comments/{announcement-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/announcement/comments/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/announcement/comments/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/document-likes/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/document-likes/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/comment-likes/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/comment-likes/{comment-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")

                        .requestMatchers(HttpMethod.POST, "/favorite/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/favorite").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/favorite/{document-id}").hasAnyRole("USER", "STUDENT", "ADMIN", "SUPERADMIN")

                        .anyRequest().denyAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                // 모든 출처 허용
                configuration.addAllowedOriginPattern("https://ptc-front-bves.vercel.app"); // allowedOriginPatterns 사용
                configuration.addAllowedOriginPattern("http://192.168.10.51:5173");
                configuration.addAllowedOriginPattern("http://192.168.45.90:5173");
                configuration.addAllowedOriginPattern("http://172.30.1.15:5173");
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true); // allowCredentials 설정
                configuration.setExposedHeaders(Arrays.asList("Authorization"));
                configuration.setMaxAge(3600L);

                return configuration;
            }
        }));
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(), jwtUtil,refreshRepository), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        return http.build();
    }
}
