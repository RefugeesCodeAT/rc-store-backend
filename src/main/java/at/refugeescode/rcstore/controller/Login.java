package at.refugeescode.rcstore.controller;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class Login {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public Boolean login(User loginUser) {
        Optional<User> optionalUser = userRepository.findOneByEmail(loginUser.getEmail());
        if (!optionalUser.isPresent()) {
            return false;
        }
        User databaseUser = optionalUser.get();
        return databaseUser.getPassword().equals(passwordEncoder.encode(loginUser.getPassword()));
    }

}
