package com.users.conf;

import com.users.entity.Users;
import com.users.repo.UserRepo;
import com.users.shared.Role;
import com.users.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BootStrapAdmin implements CommandLineRunner {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Admin Application");
        if (userRepository.findAll().isEmpty()) {
            Users user = Users.builder()
                    .firstname("Super")
                    .lastname("Admin")
                    .email("superadmin@gmail.com")
                    .fullname("Super Admin")
                    .password(passwordEncoder.encode("superadmin@1234"))
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .contactNumber("9999999999")
                    .address("UK")
                    .lastPasswordChanged(new Date())
                    .build();

            userRepository.save(user);
        }
    }
}
