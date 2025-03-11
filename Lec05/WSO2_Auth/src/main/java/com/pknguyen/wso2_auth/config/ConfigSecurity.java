package com.pknguyen.wso2_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    private final ClientRegistrationRepository clientRegistrationRepository;

    // Constructor injection để đảm bảo biến được khởi tạo
    public ConfigSecurity(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()  // Cho phép truy cập không cần xác thực
                        .anyRequest().authenticated()     // Các request khác cần xác thực
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()) // Xử lý logout OIDC
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults());// Kích hoạt OAuth2 Login

        return http.build();
    }

    /**
     * Xử lý logout thành công với OIDC
     */
    @Bean
    public LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8080"); // URL chuyển hướng sau khi logout
        return oidcLogoutSuccessHandler;
    }
}
