package com.malgn.common.config;

import java.util.List;

import com.malgn.content.domain.Content;
import com.malgn.content.repository.ContentRepository;
import com.malgn.user.domain.User;
import com.malgn.user.domain.UserRole;
import com.malgn.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.saveAll(List.of(
                    User.create("admin", passwordEncoder.encode("admin1234"), UserRole.ADMIN),
                    User.create("user1", passwordEncoder.encode("user1234"), UserRole.USER),
                    User.create("user2", passwordEncoder.encode("user1234"), UserRole.USER)
                ));
            }

            if (contentRepository.count() == 0) {
                contentRepository.saveAll(List.of(
                    Content.seed("Welcome", "Seeded content by user1", "user1"),
                    Content.seed("Admin Notice", "Seeded content by admin", "admin"),
                    Content.seed("User2 Story", "Seeded content by user2", "user2")
                ));
            }
        };
    }
}
