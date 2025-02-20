package com.indexzero.finals.config;

import com.indexzero.finals.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        // Swagger and OpenAPI Docs
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()

                        // EmployeeController for everyone
                        .requestMatchers("/api/employee/login").authenticated()
                        .requestMatchers("/api/employee/profile").authenticated()
                        .requestMatchers("/api/employee/open").authenticated()
                        // EmployeeController for admins
                        .requestMatchers("/api/employee/{login}/delete").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/{login}/{state}").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/{login}").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/all").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/{login}/update").hasAuthority("ADMIN")

                        // Entrance for everyone
                        .requestMatchers("/api/entrance").authenticated()
                        .requestMatchers("/api/entrance/last").authenticated()
                        // Entrance for admins
                        .requestMatchers("/api/entrance/all").hasAuthority("ADMIN")
                        .requestMatchers("/api/entrance/{login}").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf
                        .ignoringRequestMatchers(toH2Console())
                        .disable()).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));




        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}