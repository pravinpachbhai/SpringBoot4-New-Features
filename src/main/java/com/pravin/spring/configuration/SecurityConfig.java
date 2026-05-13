package com.pravin.spring.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.PASSWORD_AUTHORITY,
        FactorGrantedAuthority.OTT_AUTHORITY,
        FactorGrantedAuthority.WEBAUTHN_AUTHORITY

})
public class SecurityConfig {

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(
            DataSource dataSource) {
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setEnableUpdatePassword(true);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/login",
                        "/ott/**",
                        "/login/ott"
                ).permitAll()
                .anyRequest()
                .authenticated())
                .formLogin(Customizer.withDefaults())
                .webAuthn( wa -> wa
                        .allowedOrigins("http://localhost:8080")
                        .rpId("localhost")
                        .rpName("pp"))
                .oneTimeTokenLogin(ott -> ott
                        .tokenGenerationSuccessHandler(
                                new OneTimeTokenGenerationSuccessHandler() {
                                    @Override
                                    public void handle(
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            OneTimeToken oneTimeToken)
                                            throws IOException {

                                        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                                        response.getWriter().println("Check console for login URL");
                                        IO.println("Login URL for user "
                                                        + oneTimeToken.getUsername()
                                                        + " -> "
                                                        + "http://localhost:8080/login/ott?token="
                                                        + oneTimeToken.getTokenValue()
                                        );
                                    }
                                }));

        return http.build();
    }
}