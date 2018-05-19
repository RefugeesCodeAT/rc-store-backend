package at.refugeescode.rcstore.configuration;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminAccountCreator {

    @Value("${ADMIN_FIRST_NAME}")
    private String adminFirstName;
    @Value("${ADMIN_LAST_NAME}")
    private String adminLastName;
    @Value("${ADMIN_EMAIL}")
    private String adminEmail;
    @Value("${ADMIN_PASSWORD}")
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
