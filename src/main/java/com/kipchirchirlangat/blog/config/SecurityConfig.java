package com.kipchirchirlangat.blog.config;


import com.kipchirchirlangat.blog.domain.entities.User;
import com.kipchirchirlangat.blog.repositories.UserRepository;
import com.kipchirchirlangat.blog.security.BlogUserDetailService;
import com.kipchirchirlangat.blog.services.AuthenticationService;
import com.kipchirchirlangat.blog.services.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {


        BlogUserDetailService blogUserDetailService = new BlogUserDetailService(userRepository);
        //        create a new user
        String email = "johnDoe@gmail.com";
        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .name("John Doe")
                    .password(passwordEncoder().encode(("password")))
                    .build();
            return userRepository.save(newUser);
        });
        return blogUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {


        http.authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "api/v1/posts/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/categories/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/tags/**").permitAll()
                                .anyRequest().authenticated()
                ).csrf(csrf -> csrf.disable()).sessionManagement(
                        session -> session.sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
