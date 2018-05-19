package at.refugeescode.rcstore.configuration;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log
@Configuration
@RequiredArgsConstructor
public class AdminAccountCreator {

    @Value("${admin.first.name}")
    private String adminFirstName;
    @Value("${admin.last.name}")
    private String adminLastName;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            Optional<User> optionalUser = userRepository.findOneByEmail(adminEmail);
            if (optionalUser.isPresent()) {
                return;
            }
            User admin = createAdmin();
            userRepository.save(admin);
            log.info("Admin account created and saved.");
        };
    }

    private User createAdmin() {
        User admin = new User();
        admin.setFirstName(adminFirstName);
        admin.setLastName(adminLastName);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        admin.setRoles(roles);
        return admin;
    }

}
