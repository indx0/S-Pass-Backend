package com.indexzero.finals.config;

import com.indexzero.finals.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
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
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/employee/login").authenticated()
                        .requestMatchers("/api/employee/profile").authenticated()
                        .requestMatchers("/api/employee/{login}/delete").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/{login}/{state}").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/all").hasAuthority("ADMIN")
                        .requestMatchers("/api/employee/{login}").hasAuthority("ADMIN")
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf
                        .ignoringRequestMatchers(toH2Console())
                        .disable()).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));




        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}