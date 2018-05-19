package at.refugeescode.rcstore.configuration;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminAccountCreatorTest {

    @Autowired
    private UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Test
    void adminIsCreated() {
        Optional<User> optionalUser = userRepository.findOneByEmail(adminEmail);
        assertTrue(optionalUser.isPresent());

        User databaseAdmin = optionalUser.get();

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        assertEquals(databaseAdmin.getRoles(), roles);
    }

}