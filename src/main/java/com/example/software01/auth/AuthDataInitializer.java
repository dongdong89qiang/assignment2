package com.example.software01.auth;

import com.example.software01.auth.entity.AuthUser;
import com.example.software01.auth.repository.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthDataInitializer {

    @Bean
    CommandLineRunner seedAuthUsers(AuthUserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            AuthUser user = new AuthUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            repository.save(user);
        };
    }
}
